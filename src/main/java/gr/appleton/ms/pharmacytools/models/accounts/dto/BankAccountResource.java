package gr.appleton.ms.pharmacytools.models.accounts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * The type Bank account resource.
 */
@Getter
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class BankAccountResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final BankAccountModel model;
    private final List<BankAccountModel> models;

    /**
     * Instantiates a new Bank account resource.
     *
     * @param common the common model
     * @param model  the bank account model
     */
    public BankAccountResource(final CommonModel common, final BankAccountModel model) {
        this.common = common;
        this.model = model;
        this.models = null;
    }

    /**
     * Instantiates a new Bank account resource.
     *
     * @param common the common model
     * @param models the bank account models
     */
    public BankAccountResource(final CommonModel common, final List<BankAccountModel> models) {
        this.common = common;
        this.model = null;
        this.models = models;
    }

}
