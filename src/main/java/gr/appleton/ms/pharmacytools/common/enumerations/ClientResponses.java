package gr.appleton.ms.pharmacytools.common.enumerations;

/**
 * The enumeration with all responses sent to Client.
 */
public enum ClientResponses {

    /**
     * Ok client responses.
     */
    OK(1, "Smooth"),

    /**
     * The Nok.
     */
    NOK(0, "Please try again"),

    /**
     * Unauth client responses.
     */
    UNAUTH(-2, "Leave"),

    /**
     * The Inactive user.
     */
    INACTIVE_USER(-3, "Activate your account");

    private final int code;
    private final String message;

    ClientResponses(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ClientResponses{" + "code=" + code + ", message='" + message + '\'' + '}';
    }

}
