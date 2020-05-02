package be.nelcea.unifi.model;

/**
 * Java class description...
 * <p>
 * Date : 29/04/2020
 *
 * @author Eric Bariaux
 */
public class Login {

    private String username;
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
