package gr.appleton.ms.pharmacytools.authorization.dto;

import lombok.Getter;
import lombok.ToString;

/**
 * The type Change pass.
 */
@Getter
@ToString
public final class ChangePass {

    private final String pass;
    private final String updatedPass;

    /**
     * Instantiates a new Change pass.
     *
     * @param pass        the pass
     * @param updatedPass the updated pass
     */
    public ChangePass(final String pass, final String updatedPass) {
        this.pass = pass;
        this.updatedPass = updatedPass;
    }

}
