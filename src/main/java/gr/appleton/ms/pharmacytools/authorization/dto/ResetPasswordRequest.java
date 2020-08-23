package gr.appleton.ms.pharmacytools.authorization.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;

/**
 * The type Reset password request.
 */
@Getter
@ToString
public class ResetPasswordRequest {

    @Email
    private final String email;
    private final String loginUrl;

    /**
     * Instantiates a new Reset password request.
     *
     * @param email    the email
     * @param loginUrl the login url
     */
    public ResetPasswordRequest(@Email final String email, final String loginUrl) {
        this.email = email;
        this.loginUrl = loginUrl;
    }

}
