package gr.appleton.ms.pharmacytools.models.notes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * The type Note Resource.
 */
@Getter
@Slf4j
@JsonInclude(Include.NON_NULL)
public final class NoteResource {

    @JsonUnwrapped
    private final CommonModel common;
    private final NoteModel model;
    private final List<NoteModel> models;

    /**
     * Instantiates a new Note resource.
     *
     * @param common the common model
     * @param model  the note model
     */
    public NoteResource(final CommonModel common, final NoteModel model) {
        this.common = common;
        this.model = model;
        this.models = null;
    }

    /**
     * Instantiates a new Note resource.
     *
     * @param common the common
     * @param models the note models
     */
    public NoteResource(final CommonModel common, final List<NoteModel> models) {
        this.common = common;
        this.model = null;
        this.models = models;
    }

}
