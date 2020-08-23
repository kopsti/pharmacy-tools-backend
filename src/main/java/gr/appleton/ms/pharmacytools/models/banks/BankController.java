package gr.appleton.ms.pharmacytools.models.banks;

import gr.appleton.ms.pharmacytools.common.constants.Endpoints;
import gr.appleton.ms.pharmacytools.common.crud.AbstractControllerCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import gr.appleton.ms.pharmacytools.models.banks.dto.BankDao;
import gr.appleton.ms.pharmacytools.models.banks.dto.BankListResource;
import gr.appleton.ms.pharmacytools.models.banks.dto.BankModel;
import gr.appleton.ms.pharmacytools.models.banks.dto.BankResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller responsible to handle requests for Bank instances management.
 */
@CrossOrigin
@RestController
@RequestMapping(value = Endpoints.BANKS, produces = MediaTypes.HAL_JSON_VALUE)
public class BankController extends AbstractControllerCrud<BankResource, BankListResource, BankModel, BankDao> {

    private static final ClientResponses OK = ClientResponses.OK;

    private final BankService service;

    /**
     * Instantiates a new Business controller.
     *
     * @param service the bank service
     */
    @Autowired
    public BankController(final BankService service) {
        this.service = service;
    }

    @Override
    public CrudService<BankModel, BankDao> service() {
        return service;
    }

    @Override
    public BankResource getResource(final BankModel getModel, final boolean withCommon) {
        return new BankResource(new CommonModel(null, OK.getCode(), OK.getMessage()), getModel, withCommon);
    }

    @Override
    public BankListResource getListResource(final CollectionModel<BankResource> collectionModel) {
        return new BankListResource(new CommonModel(null, OK.getCode(), OK.getMessage()), collectionModel);
    }

}
