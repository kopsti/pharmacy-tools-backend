package gr.appleton.ms.pharmacytools.models.banks.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;

/**
 * The type Bank list resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public class BankListResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final CollectionModel<BankResource> models;

    /**
     * Instantiates a new Bank list resource.
     *
     * @param common the common model
     * @param models the list of bank resources
     */
    public BankListResource(final CommonModel common, final CollectionModel<BankResource> models) {
        this.common = common;
        this.models = models;
    }

}
