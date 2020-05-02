package be.nelcea.unifi.model;

import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Java class description...
 * <p>
 * Date : 01/05/2020
 *
 * @author Eric Bariaux
 */
public class PortOverrides {

    private List<PortOverride> overrides;

    public PortOverrides(List<Object> overrides) {
        this.overrides = overrides.stream().map(overrideMap -> new PortOverride((Map<String, Object>) overrideMap)).collect(Collectors.toList());
    }

    public Optional<PortOverride> overrideForPort(int portNumber) {
        return overrides.stream().filter(o -> o.getPort_idx() == portNumber).findFirst();
    }

    public String toJSONString() {
        var map = new HashMap<String, Object>();
        map.put("port_overrides", overrides);
        try {
            return JSON.std.asString(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
