package gr.appleton.ms.pharmacytools.models.verbals.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * The type Verbal resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public final class VerbalResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final VerbalModel model;
    private final List<VerbalModel> models;

    /**
     * Instantiates a new Verbal resource.
     *
     * @param common the common model
     * @param model  the verbal model
     */
    public VerbalResource(final CommonModel common, final VerbalModel model) {
        this.common = common;
        this.model = model;
        this.models = null;
    }

    /**
     * Instantiates a new Verbal resource.
     *
     * @param common the common model
     * @param models the verbal models
     */
    public VerbalResource(final CommonModel common, final List<VerbalModel> models) {
        this.common = common;
        this.model = null;
        this.models = models;
    }

}
