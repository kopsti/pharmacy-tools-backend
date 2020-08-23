package gr.appleton.ms.pharmacytools.models.verbals.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;

/**
 * The type Verbal list resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public final class VerbalListResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final CollectionModel<VerbalResource> models;

    /**
     * Instantiates a new Verbal list resource.
     *
     * @param common the common model
     * @param models the list of verbal resources
     */
    public VerbalListResource(final CommonModel common, final CollectionModel<VerbalResource> models) {
        this.common = common;
        this.models = models;
    }

}
