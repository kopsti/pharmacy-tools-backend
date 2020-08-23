package gr.appleton.ms.pharmacytools.authorization.dto;

import gr.appleton.ms.pharmacytools.common.constants.ExceptionMessages;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;

/**
 * The type Change pass.
 */
@Getter
@ToString
public final class ChangePass extends AuthModel {

    private final String updatedPass;

    /**
     * Instantiates a new Change pass.
     *
     * @param username    the username
     * @param pass        the pass
     * @param updatedPass the updated pass
     */
    public ChangePass(@Email(message = ExceptionMessages.E1) final String username,
                      final String pass, final String updatedPass) {
        super(username, pass);
        this.updatedPass = updatedPass;
    }

}
