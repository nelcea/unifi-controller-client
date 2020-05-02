package be.nelcea.unifi.model;

import com.fasterxml.jackson.jr.ob.JSON;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Java class description...
 * <p>
 * Date : 30/04/2020
 *
 * @author Eric Bariaux
 */
public class Response {

    ResponseMeta meta;
    List<Map<String, Object>> data;

    public ResponseMeta getMeta() {
        return meta;
    }

    public void setMeta(ResponseMeta meta) {
        this.meta = meta;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

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
