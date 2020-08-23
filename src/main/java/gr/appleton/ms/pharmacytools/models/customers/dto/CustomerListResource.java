package gr.appleton.ms.pharmacytools.models.customers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;

/**
 * The type Customer list resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public final class CustomerListResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final CollectionModel<CustomerResource> models;

    /**
     * Instantiates a new Customer list resource.
     *
     * @param common the common model
     * @param models the list of customer resources
     */
    public CustomerListResource(final CommonModel common, final CollectionModel<CustomerResource> models) {
        this.common = common;
        this.models = models;
    }

}
