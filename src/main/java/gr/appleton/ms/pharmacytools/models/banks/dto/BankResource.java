package gr.appleton.ms.pharmacytools.models.banks.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * The type Bank resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public final class BankResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final BankModel model;
    private final List<BankModel> models;

    /**
     * Instantiates a new Bank resource.
     *
     * @param common the common model
     * @param model  the bank model
     */
    public BankResource(final CommonModel common, final BankModel model) {
        this.common = common;
        this.model = model;
        this.models = null;
    }

    /**
     * Instantiates a new Bank resource.
     *
     * @param common the common model
     * @param models the bank models
     */
    public BankResource(final CommonModel common, final List<BankModel> models) {
        this.common = common;
        this.model = null;
        this.models = models;
    }

}
