package gr.appleton.ms.pharmacytools.models.verbals;

import gr.appleton.ms.pharmacytools.common.constants.Endpoints;
import gr.appleton.ms.pharmacytools.common.crud.AbstractControllerCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import gr.appleton.ms.pharmacytools.models.verbals.dto.VerbalDao;
import gr.appleton.ms.pharmacytools.models.verbals.dto.VerbalModel;
import gr.appleton.ms.pharmacytools.models.verbals.dto.VerbalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The controller responsible to handle requests for Verbal instances management.
 */
@CrossOrigin
@RestController
@RequestMapping(value = Endpoints.VERBALS)
public class VerbalController extends AbstractControllerCrud<VerbalResource, VerbalModel, VerbalDao> {

    private static final ClientResponses OK = ClientResponses.OK;

    private final VerbalService service;

    /**
     * Instantiates a new Verbal controller.
     *
     * @param service the verbal service
     */
    @Autowired
    public VerbalController(final VerbalService service) {
        this.service = service;
    }

    @Override
    public CrudService<VerbalModel, VerbalDao> service() {
        return service;
    }

    @Override
    public VerbalResource getResource(final VerbalModel getModel) {
        return new VerbalResource(new CommonModel(null, OK.getCode(), OK.getMessage()), getModel);
    }

    @Override
    public VerbalResource getListResource(final List<VerbalModel> models) {
        return new VerbalResource(new CommonModel(null, OK.getCode(), OK.getMessage()), models);
    }

}
