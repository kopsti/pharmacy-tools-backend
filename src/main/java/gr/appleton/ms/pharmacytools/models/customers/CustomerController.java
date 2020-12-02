package gr.appleton.ms.pharmacytools.models.customers;

import gr.appleton.ms.pharmacytools.common.constants.Endpoints;
import gr.appleton.ms.pharmacytools.common.crud.AbstractControllerCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerDao;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerModel;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The controller responsible to handle requests for Customer instances management.
 */
@CrossOrigin
@RestController
@RequestMapping(value = Endpoints.CUSTOMERS)
public class CustomerController extends AbstractControllerCrud<CustomerResource, CustomerModel, CustomerDao> {

    private static final ClientResponses OK = ClientResponses.OK;

    private final CustomerService service;

    /**
     * Instantiates a new Customer controller.
     *
     * @param service the customer service
     */
    @Autowired
    public CustomerController(final CustomerService service) {
        this.service = service;
    }

    @Override
    public CrudService<CustomerModel, CustomerDao> service() {
        return service;
    }

    @Override
    public CustomerResource getResource(final CustomerModel getModel) {
        return new CustomerResource(new CommonModel(null, OK.getCode(), OK.getMessage()), getModel);
    }

    @Override
    public CustomerResource getListResource(final List<CustomerModel> models) {
        return new CustomerResource(new CommonModel(null, OK.getCode(), OK.getMessage()), models);
    }

}
