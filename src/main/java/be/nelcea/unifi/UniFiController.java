package be.nelcea.unifi;

import be.nelcea.unifi.exceptions.InvalidLoginException;
import be.nelcea.unifi.exceptions.OperationFailedException;
import be.nelcea.unifi.model.Login;
import be.nelcea.unifi.model.Response;
import be.nelcea.unifi.utils.JacksonJrBodyHandler;
import com.fasterxml.jackson.jr.ob.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Properties;

/**
 * Java class description...
 * <p>
 * Date : 29/04/2020
 *
 * @author Eric Bariaux
 */
public class UniFiController {

    private String baseURI;
    private String site = "default";

    private CookieManager cookieManager;
    private HttpClient client;

    private static final Logger LOG = LoggerFactory.getLogger(UniFiController.class);

    // TODO: javadoc

    public UniFiController(String baseURI) {
        this(baseURI, false);
    }

    public UniFiController(String baseURI, boolean skipSecurityChecks) {
        this.baseURI = baseURI;

        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL); // TODO: only accepts desired cookies

        if (skipSecurityChecks) {
            final Properties props = System.getProperties();
            props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());
            // TODO: this is global, and influences all HTTP calls, is there a more local way to do it ?

            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new SecureRandom());

            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }
            client = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .cookieHandler(cookieManager)
                    .build();
        } else {
            client = HttpClient.newBuilder()
                    .cookieHandler(cookieManager)
                    .build();
        }
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void login(String username, String password) throws InvalidLoginException, OperationFailedException {
        // This call allows detection of UniFi OS, not used for now for UDM
        // HttpResponse<Response> response = executeRequest(emptyPostRequest(""));
        /*
        Root URL: 302
        java.net.http.HttpHeaders@2542b783 { {content-length=[0], date=[Thu, 30 Apr 2020 05:45:40 GMT], location=[/manage]} }
        Redirect to provided location /manage
         */


        try {
            String payload = JSON.std.asString(new Login(username, password));
            HttpResponse<Response> response = executeRequest(postRequest("/api/login", payload));

            // Invalid passwords gives a response 400 with body {"meta":{"rc":"error","msg":"api.err.Invalid"},"data":[]}
            if (response.statusCode() == 400 && "error".equals(response.body().getMeta().getRc())) {
                throw new InvalidLoginException();
            }
        } catch (IOException e) {
            throw new OperationFailedException("Could not login", e);
        }
    }

    public void logout() throws OperationFailedException {
        HttpResponse<Response> response = executeRequest(emptyPostRequest("/logout"));

        // Successfully logout does remove cookie, if it failed, do we want to remove it ourself ?
        //        cookieManager.getCookieStore().removeAll();
        // Note that calling logout when already logged out works OK
    }

    private boolean isLoggedIn() {
        return !cookieManager.getCookieStore().getCookies().isEmpty();
    }

    public Response listDevices(final String mac) throws OperationFailedException {
        HttpResponse<Response> response = executeRequest(getRequest("/api/s/" + site + "/stat/device/" + mac));
        if (response.statusCode() != 200) {
            throw new OperationFailedException("Call failed with status " + response.statusCode());
        }
        return response.body();
    }

    public void setDeviceSettingsBase(String deviceId, String payload) throws OperationFailedException {
        HttpResponse<Response> response = executeRequest(putRequest("/api/s/" + site + "/rest/device/" + deviceId, payload));
        // If invalid device id -> 400, {"meta":{"rc":"error","msg":"api.err.IdInvalid"},"data":[]}
        if (response.statusCode() == 400) {
            throw new OperationFailedException("Call failed with status 400, msg " + response.body().getMeta().getMsg());
        }
        if (response.statusCode() != 200) {
            throw new OperationFailedException("Call failed with status " + response.statusCode());
        }
        // If invalid payload -> 200, {"meta":{"rc":"ok"},"data":[]}
    }

    private HttpRequest putRequest(String path, String payload) {
        return HttpRequest.newBuilder()
                    .uri(URI.create(baseURI + path).normalize())
                    .timeout(Duration.ofMinutes(2))
                    .header("Content-Type", "application/json")
                    .header("charset", "utf-8")
                    .PUT(HttpRequest.BodyPublishers.ofString(payload))
                    .build();
    }

    private HttpRequest getRequest(String path) {
        return HttpRequest.newBuilder()
                .uri(URI.create(baseURI + path).normalize())
                .timeout(Duration.ofMinutes(2))
                .GET()
                .build();
    }

    private HttpRequest postRequest(String path, String payload) {
        return HttpRequest.newBuilder()
                .uri(URI.create(baseURI + path).normalize())
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .header("charset", "utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
    }

    private HttpRequest emptyPostRequest(String path) {
        return HttpRequest
                .newBuilder()
                .uri(URI.create(baseURI + path).normalize())
                .timeout(Duration.ofMinutes(2))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
    }

    public HttpResponse<Response> executeRequest(HttpRequest request) throws OperationFailedException {
        try {
            HttpResponse<Response> response = client.send(request, new JacksonJrBodyHandler<Response>(Response.class));
            LOG.trace("Request: " + request);
            LOG.trace("Response status code: " + response.statusCode());
            LOG.trace("Response headers: " + response.headers());
            LOG.trace("Response body: "+ response.body());
            LOG.trace("Cookies after request: " + cookieManager.getCookieStore().getCookies());
            return response;
        } catch (IOException | InterruptedException e) {
            throw new OperationFailedException("Could not execute request", e);
        }
    }

    private static TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            }
    };

}
