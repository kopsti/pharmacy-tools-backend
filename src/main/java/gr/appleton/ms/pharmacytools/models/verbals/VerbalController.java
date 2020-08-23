package gr.appleton.ms.pharmacytools.models.verbals;

import gr.appleton.ms.pharmacytools.common.constants.Endpoints;
import gr.appleton.ms.pharmacytools.common.crud.AbstractControllerCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import gr.appleton.ms.pharmacytools.models.verbals.dto.VerbalDao;
import gr.appleton.ms.pharmacytools.models.verbals.dto.VerbalListResource;
import gr.appleton.ms.pharmacytools.models.verbals.dto.VerbalModel;
import gr.appleton.ms.pharmacytools.models.verbals.dto.VerbalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller responsible to handle requests for Verbal instances management.
 */
@CrossOrigin
@RestController
@RequestMapping(value = Endpoints.VERBALS, produces = MediaTypes.HAL_JSON_VALUE)
public class VerbalController
    extends AbstractControllerCrud<VerbalResource, VerbalListResource, VerbalModel, VerbalDao> {

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
    public VerbalResource getResource(final VerbalModel getModel, final boolean withCommon) {
        return new VerbalResource(new CommonModel(null, OK.getCode(), OK.getMessage()), getModel, withCommon);
    }

    @Override
    public VerbalListResource getListResource(final CollectionModel<VerbalResource> collectionModel) {
        return new VerbalListResource(new CommonModel(null, OK.getCode(), OK.getMessage()), collectionModel);
    }

}
