package gr.appleton.ms.pharmacytools.models.orders.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * The type Order resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public final class OrderResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final OrderModel model;
    private final List<OrderModel> models;

    /**
     * Instantiates a new Order resource.
     *
     * @param common the common model
     * @param model  the order model
     */
    public OrderResource(final CommonModel common, final OrderModel model) {
        this.common = common;
        this.model = model;
        this.models = null;
    }

    /**
     * Instantiates a new Order resource.
     *
     * @param common the common model
     * @param models the order models
     */
    public OrderResource(final CommonModel common, final List<OrderModel> models) {
        this.common = common;
        this.models = models;
        this.model = null;
    }

}
