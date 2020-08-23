package gr.appleton.ms.pharmacytools.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.ToString;

/**
 * The Class for a Common model.
 */
@Getter
@ToString
@JsonInclude(Include.NON_NULL)
public final class CommonModel {

    private final String token;
    private final Integer code;
    private final String message;

    /**
     * Instantiates a new Common Model.
     *
     * @param token   the token
     * @param code    the code
     * @param message the message
     */
    public CommonModel(final String token, final Integer code, final String message) {
        this.token = token;
        this.code = code;
        this.message = message;
    }

}
