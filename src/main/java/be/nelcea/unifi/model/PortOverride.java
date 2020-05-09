package be.nelcea.unifi.model;

import java.util.List;
import java.util.Map;

/**
 * <p>The port override configuration for a single port.
 *
 * @author Eric Bariaux
 */
public class PortOverride {

    private Map<String, Object> rawMap;

    public PortOverride(Map<String, Object> rawMap) {
        this.rawMap = rawMap;
    }

    public int getPort_idx() {
        return (int) rawMap.get("port_idx");
    }

    public String getPortconf_id() {
        return (String) rawMap.get("portconf_id");
    }

    public String getName() {
        return (String) rawMap.get("name");
    }

    public PoEMode getPoe_mode() {
        return PoEMode.valueOf(((String) rawMap.get("poe_mode")).toUpperCase());
    }

    public void setPoe_mode(PoEMode poeMode) {
        rawMap.put("poe_mode", poeMode.toString());
    }

    public List getPort_security_mac_address() {
        return (List) rawMap.get("port_security_mac_address");
    }

    /*
      This is a sample JSON of what the API corresponding section contains.
        {
            "port_idx": 4,
            "portconf_id": "5e...b0", // 24 chars long
            "name": "a name",
            "poe_mode": "off",
            "port_security_mac_address": [

            ]
        }

        Initial idea was to store them as specific member variables but it does not brind any value at this stage
        and storing elements in a Map is easier.
            private int port_idx;
            private String portconf_id;
            private String name;
            private PoEMode poe_mode;
            private List port_security_mac_address;
   */
}
