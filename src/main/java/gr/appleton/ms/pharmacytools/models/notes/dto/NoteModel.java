package gr.appleton.ms.pharmacytools.models.notes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import gr.appleton.ms.pharmacytools.common.crud.AbstractModel;
import gr.appleton.ms.pharmacytools.common.dto.ModelCommonProperties;
import lombok.Data;

/**
 * The base model for Note instances.
 */
@Data
@JsonInclude(Include.NON_NULL)
public class NoteModel extends AbstractModel {

    private String content;
    private boolean important;
    private boolean completed;

    //GET
    private ModelCommonProperties common;

    //POST
    private String comments;

}
