package gr.appleton.ms.pharmacytools.models.suppliers;

import gr.appleton.ms.pharmacytools.common.constants.Endpoints;
import gr.appleton.ms.pharmacytools.common.crud.AbstractControllerCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import gr.appleton.ms.pharmacytools.models.suppliers.dto.SupplierDao;
import gr.appleton.ms.pharmacytools.models.suppliers.dto.SupplierModel;
import gr.appleton.ms.pharmacytools.models.suppliers.dto.SupplierResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The controller responsible to handle requests for Supplier instances management.
 */
@CrossOrigin
@RestController
@RequestMapping(value = Endpoints.SUPPLIERS)
public class SupplierController extends AbstractControllerCrud<SupplierResource, SupplierModel, SupplierDao> {

    private static final ClientResponses OK = ClientResponses.OK;

    private final SupplierService service;

    /**
     * Instantiates a new Supplier controller.
     *
     * @param service the supplier service
     */
    @Autowired
    public SupplierController(final SupplierService service) {
        this.service = service;
    }

    @Override
    public CrudService<SupplierModel, SupplierDao> service() {
        return service;
    }

    @Override
    public SupplierResource getResource(final SupplierModel getModel) {
        return new SupplierResource(new CommonModel(null, OK.getCode(), OK.getMessage()), getModel);
    }

    @Override
    public SupplierResource getListResource(final List<SupplierModel> models) {
        return new SupplierResource(new CommonModel(null, OK.getCode(), OK.getMessage()), models);
    }

}
