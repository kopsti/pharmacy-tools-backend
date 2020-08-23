package gr.appleton.ms.pharmacytools.models.customers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import gr.appleton.ms.pharmacytools.common.crud.AbstractModel;
import gr.appleton.ms.pharmacytools.common.dto.ModelCommonProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The base model for Customer instances.
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CustomerModel extends AbstractModel {

    private String firstName;
    private String lastName;
    private boolean vip;
    private String homePhoneNumber;
    private String mobilePhoneNumber;
    private String email;
    private String address;

    //GET
    private ModelCommonProperties common;

    //POST
    private String comments;

}
