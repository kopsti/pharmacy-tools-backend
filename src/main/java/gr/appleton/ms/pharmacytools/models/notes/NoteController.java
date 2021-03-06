package gr.appleton.ms.pharmacytools.models.notes;

import gr.appleton.ms.pharmacytools.common.constants.Endpoints;
import gr.appleton.ms.pharmacytools.common.crud.AbstractControllerCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import gr.appleton.ms.pharmacytools.models.notes.dto.NoteDao;
import gr.appleton.ms.pharmacytools.models.notes.dto.NoteModel;
import gr.appleton.ms.pharmacytools.models.notes.dto.NoteResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The controller responsible to handle requests for Note instances management.
 */
@CrossOrigin
@RestController
@RequestMapping(value = Endpoints.NOTES)
public class NoteController extends AbstractControllerCrud<NoteResource, NoteModel, NoteDao> {

    private static final ClientResponses OK = ClientResponses.OK;

    private final NoteService service;

    /**
     * Instantiates a new Note controller.
     *
     * @param service the note service
     */
    @Autowired
    public NoteController(final NoteService service) {
        this.service = service;
    }

    @Override
    public CrudService<NoteModel, NoteDao> service() {
        return service;
    }

    @Override
    public NoteResource getResource(final NoteModel getModel) {
        return new NoteResource(new CommonModel(null, OK.getCode(), OK.getMessage()), getModel);
    }

    @Override
    public NoteResource getListResource(final List<NoteModel> models) {
        return new NoteResource(new CommonModel(null, OK.getCode(), OK.getMessage()), models);
    }

}
