package gr.appleton.ms.pharmacytools.authorization.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Data;
import lombok.ToString;

/**
 * The type Login response.
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class LoginResponse {

    @JsonUnwrapped
    private final CommonModel common;
    private final String username;
    private final String role;

    /**
     * Instantiates a new Login response.
     *
     * @param common   the common
     * @param username the username
     * @param role     the role
     */
    public LoginResponse(final CommonModel common, final String username, final String role) {
        this.common = common;
        this.username = username;
        this.role = role;
    }

}
