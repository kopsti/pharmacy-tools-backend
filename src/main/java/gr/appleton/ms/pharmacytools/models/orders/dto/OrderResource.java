package gr.appleton.ms.pharmacytools.models.orders.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.models.customers.CustomerController;
import gr.appleton.ms.pharmacytools.models.orders.OrderController;
import gr.appleton.ms.pharmacytools.models.suppliers.SupplierController;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * The type Order resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public final class OrderResource extends RepresentationModel<OrderResource> {

    @JsonUnwrapped
    private final CommonModel common;
    private final OrderModel model;

    /**
     * Instantiates a new Order resource.
     *
     * @param common     the common model
     * @param model      the order model
     * @param withCommon the with common flag
     */
    public OrderResource(final CommonModel common, final OrderModel model, final boolean withCommon) {
        this.common = withCommon ? common : null;
        this.model = model;

        if (withCommon) {
            add(linkTo(OrderController.class).withRel("all"));
        }

        try {
            add(linkTo(methodOn(CustomerController.class).retrieveById(model.getCustomer().getId(), true)).withRel("customer"));
        } catch (final GenericException e) {
            log.warn(e.getMessage(), e);
        }

        try {
            add(linkTo(methodOn(SupplierController.class).retrieveById(model.getSupplier().getId(), true)).withRel("supplier"));
        } catch (final GenericException e) {
            log.warn(e.getMessage(), e);
        }

        try {
            add(linkTo(methodOn(OrderController.class).retrieveById(model.getId(), true)).withSelfRel());
        } catch (final GenericException e) {
            log.warn(e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final OrderResource that = (OrderResource) o;
        return model.getId() == that.model.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), model);
    }

}
