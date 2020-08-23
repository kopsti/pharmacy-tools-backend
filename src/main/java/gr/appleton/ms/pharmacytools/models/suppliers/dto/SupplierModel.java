package gr.appleton.ms.pharmacytools.models.suppliers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gr.appleton.ms.pharmacytools.common.crud.AbstractModel;
import gr.appleton.ms.pharmacytools.common.dto.ModelCommonProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The base model for Supplier instances.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplierModel extends AbstractModel {

    private String title;
    private String description;
    private String phoneNumber;
    private String email;
    private String taxId;
    private String taxAuthority;

    //GET
    private ModelCommonProperties common;

    //POST
    private String comments;

}
