package gr.appleton.ms.pharmacytools.authorization.enumerations;

/**
 * The enum Authentication status.
 */
public enum AuthenticationStatus {

    /**
     * Ok authentication status.
     */
    OK(1),
    /**
     * User not found authentication status.
     */
    USER_NOT_FOUND(-1),
    /**
     * User not active authentication status.
     */
    USER_NOT_ACTIVE(0),
    /**
     * Other error authentication status.
     */
    OTHER_ERROR(-2);

    private final int code;

    AuthenticationStatus(final int code) {
        this.code = code;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "AuthenticationStatus{" + "code=" + code + '}';
    }
}
