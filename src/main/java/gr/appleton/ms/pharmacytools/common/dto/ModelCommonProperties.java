package gr.appleton.ms.pharmacytools.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * The Class used for some common properties for every model.
 */
@Getter
@AllArgsConstructor
@ToString
@JsonInclude(Include.NON_NULL)
public final class ModelCommonProperties {

    private final Date insertTimestamp;
    private final Date updateTimestamp;
    private final String comments;
    private final boolean deleted;
    private final Date deleteTimestamp;

}
