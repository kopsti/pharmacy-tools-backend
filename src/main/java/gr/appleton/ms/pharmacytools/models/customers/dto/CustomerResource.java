package gr.appleton.ms.pharmacytools.models.customers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * The type Customer resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public final class CustomerResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final CustomerModel model;
    private final List<CustomerModel> models;

    /**
     * Instantiates a new Customer resource.
     *
     * @param common the common model
     * @param model  the customer model
     */
    public CustomerResource(final CommonModel common, final CustomerModel model) {
        this.common = common;
        this.model = model;
        this.models = null;
    }

    /**
     * Instantiates a new Customer resource.
     *
     * @param common the common model
     * @param models the customer models
     */
    public CustomerResource(final CommonModel common, final List<CustomerModel> models) {
        this.common = common;
        this.model = null;
        this.models = models;
    }

}
