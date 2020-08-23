package gr.appleton.ms.pharmacytools.models.banks.dto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.models.banks.BankController;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * The type Bank resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public final class BankResource extends RepresentationModel<BankResource> {

    @JsonUnwrapped
    private final CommonModel common;
    private final BankModel model;

    /**
     * Instantiates a new Bank resource.
     *
     * @param common     the common model
     * @param model      the bank model
     * @param withCommon the with common flag
     */
    public BankResource(final CommonModel common, final BankModel model, final boolean withCommon) {
        this.common = withCommon ? common : null;
        this.model = model;

        if (withCommon) {
            add(linkTo(BankController.class).withRel("all"));
        }

        try {
            add(linkTo(methodOn(BankController.class).retrieveById(model.getId(), true)).withSelfRel());
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
        final BankResource that = (BankResource) o;
        return model.getId() == that.model.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), model);
    }

}
