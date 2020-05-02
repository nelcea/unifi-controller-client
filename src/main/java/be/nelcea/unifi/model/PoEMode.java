package be.nelcea.unifi.model;

/**
 * Java class description...
 * <p>
 * Date : 30/04/2020
 *
 * @author Eric Bariaux
 */
public enum PoEMode {
    OFF,
    AUTO;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
