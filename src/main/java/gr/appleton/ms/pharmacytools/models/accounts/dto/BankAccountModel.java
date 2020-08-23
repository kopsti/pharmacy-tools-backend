package gr.appleton.ms.pharmacytools.models.accounts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gr.appleton.ms.pharmacytools.common.crud.AbstractModel;
import gr.appleton.ms.pharmacytools.common.dto.ModelCommonProperties;
import gr.appleton.ms.pharmacytools.models.banks.dto.BankModel;
import gr.appleton.ms.pharmacytools.models.suppliers.dto.SupplierModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The base model for Bank Account instances.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankAccountModel extends AbstractModel {

    private String iban;
    private String number;

    //GET
    private BankModel bank;
    private SupplierModel supplier;
    private ModelCommonProperties common;

    //POST
    private Long bankId;
    private Long supplierId;
    private String comments;

}
