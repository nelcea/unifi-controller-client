package be.nelcea.unifi.utils;

import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;

/**
 * Java class description...
 * <p>
 * Date : 30/04/2020
 *
 * @author Eric Bariaux
 */
public class JacksonJrBodyHandler<T> implements HttpResponse.BodyHandler {

    private final Class<T> type;

    public JacksonJrBodyHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    public HttpResponse.BodySubscriber apply(HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofByteArray(),
                byteArray -> {
                    try {
                        return (byteArray.length == 0?null:JSON.std.beanFrom(type, byteArray));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }
}
