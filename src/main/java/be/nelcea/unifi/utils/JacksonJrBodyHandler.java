package be.nelcea.unifi.utils;

import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;
import java.net.http.HttpResponse;

/**
 * <p>A {@link HttpResponse.BodyHandler} that decodes the JSON response body into a bean of a given class.
 * <p>The JSON parsing is implemented using JacksonJr.
 *
 * @author Eric Bariaux
 */
public class JacksonJrBodyHandler<T> implements HttpResponse.BodyHandler {

    private final Class<T> type;

    /**
     * Constructs a {@code JacksonJrBodyHandler} to decode the respose body into the given bean class.
     *
     * @param type the {@code Class} of the bean to construct
     */
    public JacksonJrBodyHandler(Class<T> type) {
        this.type = type;
    }

    /**
     * Processes the response and creates the desired bean from its body content.
     *
     * @param responseInfo the {@code ResponseInfo} whose's body to process
     * @return a bean populated from the JSON content of the body
     */
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
