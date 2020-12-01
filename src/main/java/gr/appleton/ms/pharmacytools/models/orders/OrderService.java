package gr.appleton.ms.pharmacytools.models.orders;

import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.crud.AbstractServiceCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.dto.ModelCommonProperties;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.common.utils.CommonService;
import gr.appleton.ms.pharmacytools.common.utils.GreekLatin;
import gr.appleton.ms.pharmacytools.models.customers.CustomerService;
import gr.appleton.ms.pharmacytools.models.orders.dto.OrderDao;
import gr.appleton.ms.pharmacytools.models.orders.dto.OrderModel;
import gr.appleton.ms.pharmacytools.models.suppliers.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * The service responsible to handle operations for Selling Order instances management.
 */
@Service
@Slf4j
public final class OrderService extends AbstractServiceCrud<OrderModel, OrderDao>
    implements CrudService<OrderModel, OrderDao> {

    private final OrderRepository orders;
    private final CustomerService customers;
    private final SupplierService suppliers;

    /**
     * Instantiates a new Selling Order service.
     *
     * @param common    the common service
     * @param orders    the orders repository
     * @param customers the customers service
     * @param suppliers the suppliers service
     */
    @Autowired
    public OrderService(final CommonService common, final OrderRepository orders, final CustomerService customers,
                        final SupplierService suppliers) {
        super(common);
        this.orders = orders;
        this.customers = customers;
        this.suppliers = suppliers;
    }

    @Override
    public CrudRepository<OrderDao, Long> repository() {
        return orders;
    }

    @Override
    public OrderModel dao2Model(final OrderDao dao) {
        final OrderModel model = new OrderModel();
        model.setId(dao.getId());
        try {
            model.setCustomer(customers.retrieveById(dao.getCustomer().getId()));
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
        try {
            model.setSupplier(suppliers.retrieveById(dao.getSupplier().getId()));
        } catch (final GenericException e) {
            log.error(e.getMessage(), e);
        }
        model.setExpirationTimestamp(dao.getExpirationTimestamp());
        model.setProduct(dao.getProduct());
        model.setStatus(dao.getStatus());
        model.setType(dao.getType());
        model.setProduct(dao.getProduct());
        model.setCompletionTimestamp(dao.getCompleteTimestamp());
        model.setCommon(new ModelCommonProperties(dao.getInsertTimestamp(), dao.getUpdateTimestamp(), dao.getComments(),
            dao.isDeleted(), dao.getDeleteTimestamp()));
        return model;
    }

    @Override
    public OrderDao model2Dao(final OrderModel model, OrderDao dao, final UserDao userDao)
        throws GenericException {
        if (dao == null) {
            dao = new OrderDao();
        }

        if ("COMPLETED".equals(model.getStatus())) {
            dao.setCompleteTimestamp(new Date());
        }

        dao.setOwner(userDao);
        dao.setStatus(model.getStatus());
        dao.setType(model.getType());
        dao.setProduct(model.getProduct());
        dao.setQProduct(GreekLatin.greek2latin(model.getProduct()));
        dao.setCustomer(customers.retrieveDaoById(model.getCustomerId()));
        dao.setSupplier(suppliers.retrieveDaoById(model.getSupplierId()));
        dao.setExpirationTimestamp(model.getExpirationTimestamp());
        dao.setComments(model.getComments());
        dao.setQComments(GreekLatin.greek2latin(model.getComments()));
        return dao;
    }

    @Override
    public boolean mayEnterBin() {
        return true;
    }

    @Override
    public boolean alreadyInBin(final long id) throws GenericException {
        return retrieveDaoById(id).isDeleted();
    }

    @Override
    public void addInBin(final long id) throws GenericException {
        final OrderDao order = retrieveDaoById(id);
        order.setDeleted(true);
        order.setDeleteTimestamp(new Date());
        repository().save(order);
    }

}
