package be.nelcea.unifi.model;

/**
 * <p>Meta section of the response payload received from the UniFi API.
 * <p>It has a similar structure for all API respones.
 *
 * @author Eric Bariaux
 */
public class ResponseMeta {

    private String rc;
    private String msg;

    /**
     * The return code of the operation response towards the UniFi API.
     * "ok" when the operation was successful.
     *
     * @return a {@code String} with the error code of the operation response
     */
    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    /**
     * The message of the operation response towards the UniFi API.
     *
     * @return a {@code String} with the message of the operation response
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
