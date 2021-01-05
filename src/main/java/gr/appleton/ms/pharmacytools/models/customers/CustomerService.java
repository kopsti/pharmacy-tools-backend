package gr.appleton.ms.pharmacytools.models.customers;

import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.crud.AbstractServiceCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.crud.MyCrudRepository;
import gr.appleton.ms.pharmacytools.common.dto.ModelCommonProperties;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.common.utils.CommonService;
import gr.appleton.ms.pharmacytools.common.utils.GreekLatin;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerDao;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * The service responsible to handle operations for Customer instances management.
 */
@Service
@Slf4j
public final class CustomerService extends AbstractServiceCrud<CustomerModel, CustomerDao>
    implements CrudService<CustomerModel, CustomerDao> {

    private final CustomerRepository customers;

    /**
     * Instantiates a new Customer service.
     *
     * @param common    the common service
     * @param customers the customer repository
     */
    @Autowired
    public CustomerService(final CommonService common, final CustomerRepository customers) {
        super(common);
        this.customers = customers;
    }

    @Override
    public MyCrudRepository<CustomerDao, Long> repository() {
        return customers;
    }

    @Override
    public CustomerModel dao2Model(final CustomerDao dao) {
        final CustomerModel model = new CustomerModel();
        model.setId(dao.getId());
        model.setFirstName(dao.getFirstName());
        model.setLastName(dao.getLastName());
        model.setVip(dao.isVip());
        model.setHomePhoneNumber(dao.getHomePhoneNumber());
        model.setMobilePhoneNumber(dao.getMobilePhoneNumber());
        model.setEmail(dao.getEmail());
        model.setAddress(dao.getAddress());
        model.setCommon(new ModelCommonProperties(dao.getInsertTimestamp(), dao.getUpdateTimestamp(), dao.getComments(),
            dao.isDeleted(), dao.getDeleteTimestamp()));
        return model;
    }

    @Override
    public CustomerDao model2Dao(final CustomerModel model, CustomerDao dao, final UserDao userDao) {
        if (dao == null) {
            dao = new CustomerDao();
        }
        dao.setOwner(userDao);
        dao.setFirstName(model.getFirstName());
        dao.setQFirstName(GreekLatin.greek2latin(model.getFirstName()));
        dao.setLastName(model.getLastName());
        dao.setQLastName(GreekLatin.greek2latin(model.getLastName()));
        dao.setVip(model.isVip());
        dao.setHomePhoneNumber(model.getHomePhoneNumber());
        dao.setMobilePhoneNumber(model.getMobilePhoneNumber());
        dao.setEmail(model.getEmail());
        dao.setAddress(model.getAddress());
        dao.setQAddress(GreekLatin.greek2latin(model.getAddress()));
        dao.setComments(model.getComments());
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
        final CustomerDao customer = retrieveDaoById(id);
        customer.setDeleted(true);
        customer.setDeleteTimestamp(new Date());
        repository().save(customer);
    }

}
