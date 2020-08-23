package gr.appleton.ms.pharmacytools.models.verbals.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gr.appleton.ms.pharmacytools.common.crud.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The base model for Verbal instances.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerbalModel extends AbstractModel {

    private String key;
    private String value;

}
