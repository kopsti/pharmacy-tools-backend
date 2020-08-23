package gr.appleton.ms.pharmacytools.common.exceptions;

/**
 * The exception used for inactive users.
 */
public final class InactiveUserException extends Exception {

    /**
     * Instantiates a new Inactive user exception.
     *
     * @param message the message
     */
    public InactiveUserException(final String message) {
        super(message);
    }

}
