package gr.appleton.ms.pharmacytools.models.orders;

import gr.appleton.ms.pharmacytools.common.constants.Endpoints;
import gr.appleton.ms.pharmacytools.common.crud.AbstractControllerCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import gr.appleton.ms.pharmacytools.models.orders.dto.OrderDao;
import gr.appleton.ms.pharmacytools.models.orders.dto.OrderModel;
import gr.appleton.ms.pharmacytools.models.orders.dto.OrderResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The controller responsible to handle requests for Order instances management.
 */
@CrossOrigin
@RestController
@RequestMapping(value = Endpoints.ORDERS)
public class OrderController extends AbstractControllerCrud<OrderResource, OrderModel, OrderDao> {

    private static final ClientResponses OK = ClientResponses.OK;

    private final OrderService service;

    /**
     * Instantiates a new Selling Order controller.
     *
     * @param service the order service
     */
    @Autowired
    public OrderController(final OrderService service) {
        this.service = service;
    }

    @Override
    public CrudService<OrderModel, OrderDao> service() {
        return service;
    }

    @Override
    public OrderResource getResource(final OrderModel getModel) {
        return new OrderResource(new CommonModel(null, OK.getCode(), OK.getMessage()), getModel);
    }

    @Override
    public OrderResource getListResource(final List<OrderModel> models) {
        return new OrderResource(new CommonModel(null, OK.getCode(), OK.getMessage()), models);
    }

}
