package gr.appleton.ms.pharmacytools.models.customers;

import gr.appleton.ms.pharmacytools.common.constants.Endpoints;
import gr.appleton.ms.pharmacytools.common.crud.AbstractControllerCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerDao;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerListResource;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerModel;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller responsible to handle requests for Customer instances management.
 */
@CrossOrigin
@RestController
@RequestMapping(value = Endpoints.CUSTOMERS, produces = MediaTypes.HAL_JSON_VALUE)
public class CustomerController
    extends AbstractControllerCrud<CustomerResource, CustomerListResource, CustomerModel, CustomerDao> {

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
    public CustomerResource getResource(final CustomerModel getModel, final boolean withCommon) {
        return new CustomerResource(new CommonModel(null, OK.getCode(), OK.getMessage()), getModel, withCommon);
    }

    @Override
    public CustomerListResource getListResource(final CollectionModel<CustomerResource> collectionModel) {
        return new CustomerListResource(new CommonModel(null, OK.getCode(), OK.getMessage()), collectionModel);
    }

}
