package gr.appleton.ms.pharmacytools.models.accounts;

import gr.appleton.ms.pharmacytools.common.constants.Endpoints;
import gr.appleton.ms.pharmacytools.common.crud.AbstractControllerCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import gr.appleton.ms.pharmacytools.models.accounts.dto.BankAccountDao;
import gr.appleton.ms.pharmacytools.models.accounts.dto.BankAccountModel;
import gr.appleton.ms.pharmacytools.models.accounts.dto.BankAccountResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The controller responsible to handle requests for Bank Account instances management.
 */
@CrossOrigin
@RestController
@RequestMapping(value = Endpoints.BANK_ACCOUNTS)
public class BankAccountController
    extends AbstractControllerCrud<BankAccountResource, BankAccountModel, BankAccountDao> {

    private static final ClientResponses OK = ClientResponses.OK;

    private final BankAccountService service;

    /**
     * Instantiates a new Bank Account controller.
     *
     * @param service the bank account service
     */
    @Autowired
    public BankAccountController(final BankAccountService service) {
        this.service = service;
    }

    @Override
    public CrudService<BankAccountModel, BankAccountDao> service() {
        return service;
    }

    @Override
    public BankAccountResource getResource(final BankAccountModel getModel) {
        return new BankAccountResource(new CommonModel(null, OK.getCode(), OK.getMessage()), getModel);
    }

    @Override
    public BankAccountResource getListResource(final List<BankAccountModel> models) {
        return new BankAccountResource(new CommonModel(null, OK.getCode(), OK.getMessage()), models);
    }

}
