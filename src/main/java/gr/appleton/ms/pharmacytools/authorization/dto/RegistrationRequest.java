package gr.appleton.ms.pharmacytools.authorization.dto;

import gr.appleton.ms.pharmacytools.common.constants.ExceptionMessages;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;

/**
 * The type Registration request.
 */
@Getter
@ToString(callSuper = true)
public class RegistrationRequest extends AuthModel {

    private final String role;
    private final String loginUrl;

    /**
     * Instantiates a new Registration request.
     *
     * @param username the username
     * @param pass     the pass
     * @param role     the role
     * @param loginUrl the login url
     */
    public RegistrationRequest(@Email(message = ExceptionMessages.E1) final String username, final String pass,
                               final String role, final String loginUrl) {
        super(username, pass);
        this.role = role;
        this.loginUrl = loginUrl;
    }

}
