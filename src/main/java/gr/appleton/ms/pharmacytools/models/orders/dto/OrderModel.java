package gr.appleton.ms.pharmacytools.models.orders.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import gr.appleton.ms.pharmacytools.common.crud.AbstractModel;
import gr.appleton.ms.pharmacytools.common.dto.ModelCommonProperties;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerModel;
import gr.appleton.ms.pharmacytools.models.suppliers.dto.SupplierModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * The base model for Order instances.
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class OrderModel extends AbstractModel {

    private String status;
    private String type;
    private String product;
    private Date expirationTimestamp;

    //GET
    private SupplierModel supplier;
    private CustomerModel customer;
    private Date completionTimestamp;
    private ModelCommonProperties common;

    private Long supplierId;
    private Long customerId;

    //POST
    private String comments;

}
