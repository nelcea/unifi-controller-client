package be.nelcea.unifi.model;

import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>Represents the port_overrides section of e.g. a switch configuration.
 * <p>It contains of list of {@code PortOverride} objects, for ports that have an override.
 *
 * @author Eric Bariaux
 */
public class PortOverrides {

    private List<PortOverride> overrides;

    /**
     * Constructs the {@code PortOverrides} from a list of {@code Map} representing the key/values as parsed from the API JSON payload.
     *
     * @param overrides a {@code List} of {@code Map} as parsed from the API JSON payload.
     */
    public PortOverrides(List<Object> overrides) {
        this.overrides = overrides.stream().map(overrideMap -> new PortOverride((Map<String, Object>) overrideMap)).collect(Collectors.toList());
    }

    /**
     * Search the {@code PortOverride} for the given port number, as known by the port configuration (i.e. not the index in the list).
     *
     * @param portNumber the port number in the switch configuration
     * @return an {@link Optional} describing the {@link PortOverride} for the given port number
     */
    public Optional<PortOverride> overrideForPort(int portNumber) {
        return overrides.stream().filter(o -> o.getPort_idx() == portNumber).findFirst();
    }

    /**
     * Constructs and returns the JSON snippet that can be used to set the port_overrides configuration on a device.
     *
     * @return a {@code String} with the port_overrides section as JSON
     */
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
