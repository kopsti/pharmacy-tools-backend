package gr.appleton.ms.pharmacytools.models.suppliers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;

/**
 * The type Supplier list resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public final class SupplierListResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final CollectionModel<SupplierResource> models;

    /**
     * Instantiates a new Supplier list resource.
     *
     * @param common the common model
     * @param models the list of supplier resources
     */
    public SupplierListResource(final CommonModel common, final CollectionModel<SupplierResource> models) {
        this.common = common;
        this.models = models;
    }

}
