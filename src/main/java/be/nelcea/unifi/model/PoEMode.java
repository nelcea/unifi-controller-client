package be.nelcea.unifi.model;

/**
 * <p>The PoE mode of a switch port.
 *
 * @author Eric Bariaux
 */
public enum PoEMode {
    /**
     * PoE mode off.
     */
    OFF,
    /**
     * PoE mode auto.
     */
    AUTO;

    /**
     * Returns the PoE mode in lowercase, compatible with the payload format used by the UniFi REST API.
     *
     * @return a {@code String} representing the PoE mode, in lowercase
     */
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
