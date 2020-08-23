package gr.appleton.ms.pharmacytools.models.orders.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;

/**
 * The type Order list resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public final class OrderListResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final CollectionModel<OrderResource> models;

    /**
     * Instantiates a new Order list resource.
     *
     * @param common the common model
     * @param models the list of order resources
     */
    public OrderListResource(final CommonModel common, final CollectionModel<OrderResource> models) {
        this.common = common;
        this.models = models;
    }

}
