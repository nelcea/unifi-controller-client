package be.nelcea.unifi.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Response payload received from the UniFi API.
 * <p>All responses have the same general format.
 *
 * @author Eric Bariaux
 */
public class Response {

    private ResponseMeta meta;
    private List<Map<String, Object>> data;

    /**
     * Returns the meta part of the payload.
     *
     * @return a {@code ResponseMeta} object containing the meta part of the payload.
     */
    public ResponseMeta getMeta() {
        return meta;
    }

    /**
     * Specifies the meta part of the payload.
     *
     * @param meta a {@code ResponseMeta} object representing the meta part of the payload.
     */
    public void setMeta(ResponseMeta meta) {
        this.meta = meta;
    }

    /**
     * Returns the data part of the payload.
     * This is a list of maps containing various entries specific to each endpoint response.
     *
     * @return a {@code List} of maps containing all the entries in the data part of the payload
     */
    public List<Map<String, Object>> getData() {
        return data;
    }

    /**
     * Specifies the data part of the payload.
     *
     * @param data a {@code List} of maps containing all the entries in the data part of the payload
     */
    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    /**
     * Returns the port_overrides section of the response if applicable (e.g. as is for switch devices).
     *
     * @return an {@link Optional} describing the {@link PortOverrides} section of the response.
     */
    public Optional<PortOverrides> getPortOverrides() {
        if (data.isEmpty()) {
            return Optional.empty();
        }
        if (data.get(0).get("port_overrides") == null) {
            return Optional.empty();
        }

        return Optional.of(new PortOverrides((List)data.get(0).get("port_overrides")));
    }
}
