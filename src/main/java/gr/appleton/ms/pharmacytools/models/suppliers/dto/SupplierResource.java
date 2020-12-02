package gr.appleton.ms.pharmacytools.models.suppliers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * The type Supplier Resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public final class SupplierResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final SupplierModel model;
    private final List<SupplierModel> models;

    /**
     * Instantiates a new Supplier resource.
     *
     * @param common the common model
     * @param model  the supplier model
     */
    public SupplierResource(final CommonModel common, final SupplierModel model) {
        this.common = common;
        this.model = model;
        this.models = null;
    }

    /**
     * Instantiates a new Supplier resource.
     *
     * @param common the common model
     * @param models the supplier models
     */
    public SupplierResource(final CommonModel common, final List<SupplierModel> models) {
        this.common = common;
        this.model = null;
        this.models = models;
    }

}
