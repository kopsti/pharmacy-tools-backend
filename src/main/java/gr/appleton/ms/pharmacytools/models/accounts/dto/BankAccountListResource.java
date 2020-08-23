package gr.appleton.ms.pharmacytools.models.accounts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;

/**
 * The type Bank account list resource.
 */
@Getter
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankAccountListResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final CollectionModel<BankAccountResource> models;

    /**
     * Instantiates a new Bank account list resource.
     *
     * @param common the common model
     * @param models the list of bank account resources
     */
    public BankAccountListResource(final CommonModel common, final CollectionModel<BankAccountResource> models) {
        this.common = common;
        this.models = models;
    }

}
