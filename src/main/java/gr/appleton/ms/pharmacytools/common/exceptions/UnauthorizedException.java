package gr.appleton.ms.pharmacytools.common.exceptions;

/**
 * The exception used for unauthorized requests.
 */
public class UnauthorizedException extends Exception {

    /**
     * Instantiates a new Unauthorized exception.
     *
     * @param message the message
     */
    public UnauthorizedException(final String message) {
        super(message);
    }

}
