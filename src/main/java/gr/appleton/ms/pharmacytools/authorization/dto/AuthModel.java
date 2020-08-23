package gr.appleton.ms.pharmacytools.authorization.dto;

import gr.appleton.ms.pharmacytools.common.constants.ExceptionMessages;
import lombok.Getter;

import javax.validation.constraints.Email;

/**
 * The type Auth model.
 */
@Getter
public class AuthModel {

    @Email(message = ExceptionMessages.E1)
    private final String username;
    private final String pass;

    /**
     * Instantiates a new Auth model.
     *
     * @param username the username
     * @param pass     the pass
     */
    AuthModel(@Email(message = ExceptionMessages.E1) final String username, final String pass) {
        this.username = username;
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "AuthModel{" + "username='" + username + '\'' + ", pass=<omitted on purpose>" + '}';
    }

}
