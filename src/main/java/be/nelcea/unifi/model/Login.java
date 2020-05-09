package be.nelcea.unifi.model;

/**
 * <p>Stores the credentials required for login to the controller.
 *
 * @author Eric Bariaux
 */
public class Login {

    private String username;
    private String password;

    /**
     * Constructs a {@code Login} with the specified username and password.
     *
     * @param username a {@code String} specifying the username
     * @param password a {@code String} specifying the password
     */
    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username.
     *
     * @return a {@code String} containing the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password.
     *
     * @return a {@code String} representing the password
     */
    public String getPassword() {
        return password;
    }
}
