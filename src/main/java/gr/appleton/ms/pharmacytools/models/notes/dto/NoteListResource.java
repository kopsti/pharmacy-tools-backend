package gr.appleton.ms.pharmacytools.models.notes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;

/**
 * The type Note list resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public final class NoteListResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final CollectionModel<NoteResource> models;

    /**
     * Instantiates a new Note list resource.
     *
     * @param common the common model
     * @param models the list of note resources
     */
    public NoteListResource(final CommonModel common, final CollectionModel<NoteResource> models) {
        this.common = common;
        this.models = models;
    }

}
