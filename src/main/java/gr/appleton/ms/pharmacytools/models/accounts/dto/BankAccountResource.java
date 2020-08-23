package gr.appleton.ms.pharmacytools.models.accounts.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.models.accounts.BankAccountController;
import gr.appleton.ms.pharmacytools.models.banks.BankController;
import gr.appleton.ms.pharmacytools.models.suppliers.SupplierController;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * The type Bank account resource.
 */
@Getter
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class BankAccountResource extends RepresentationModel<BankAccountResource> {

    @JsonUnwrapped
    private final CommonModel common;
    private final BankAccountModel model;

    /**
     * Instantiates a new Bank account resource.
     *
     * @param common     the common model
     * @param model      the bank account model
     * @param withCommon the with common flag
     */
    public BankAccountResource(final CommonModel common, final BankAccountModel model, final boolean withCommon) {
        this.common = withCommon ? common : null;
        this.model = model;

        if (withCommon) {
            add(linkTo(BankAccountController.class).withRel("all"));
        }

        try {
            add(linkTo(methodOn(BankController.class).retrieveById(model.getBank().getId(), true)).withRel("bank"));
        } catch (final GenericException e) {
            log.warn(e.getMessage(), e);
        }

        try {
            add(linkTo(methodOn(SupplierController.class).retrieveById(model.getSupplier().getId(), true)).withRel("supplier"));
        } catch (final GenericException e) {
            log.warn(e.getMessage(), e);
        }

        try {
            add(linkTo(methodOn(BankAccountController.class).retrieveById(model.getId(), true)).withSelfRel());
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
        final BankAccountResource that = (BankAccountResource) o;
        return model.getId() == that.model.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), common, model);
    }

}
