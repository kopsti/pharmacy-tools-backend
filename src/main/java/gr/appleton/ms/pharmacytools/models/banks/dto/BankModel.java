package gr.appleton.ms.pharmacytools.models.banks.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gr.appleton.ms.pharmacytools.common.crud.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The base model for Bank instances.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankModel extends AbstractModel {

    private String title;

}
