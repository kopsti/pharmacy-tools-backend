package gr.appleton.ms.pharmacytools.models.banks;

import gr.appleton.ms.pharmacytools.common.constants.Endpoints;
import gr.appleton.ms.pharmacytools.common.crud.AbstractControllerCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import gr.appleton.ms.pharmacytools.models.banks.dto.BankDao;
import gr.appleton.ms.pharmacytools.models.banks.dto.BankModel;
import gr.appleton.ms.pharmacytools.models.banks.dto.BankResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The controller responsible to handle requests for Bank instances management.
 */
@CrossOrigin
@RestController
@RequestMapping(value = Endpoints.BANKS)
public class BankController extends AbstractControllerCrud<BankResource, BankModel, BankDao> {

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
    public BankResource getListResource(final List<BankModel> models) {
        return new BankResource(new CommonModel(null, OK.getCode(), OK.getMessage()), models);
    }

}
