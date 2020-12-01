package gr.appleton.ms.pharmacytools.models.suppliers;

import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.crud.AbstractServiceCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.dto.ModelCommonProperties;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.common.utils.CommonService;
import gr.appleton.ms.pharmacytools.common.utils.GreekLatin;
import gr.appleton.ms.pharmacytools.models.suppliers.dto.SupplierDao;
import gr.appleton.ms.pharmacytools.models.suppliers.dto.SupplierModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * The service responsible to handle operations for Supplier instances management.
 */
@Service
@Slf4j
public final class SupplierService extends AbstractServiceCrud<SupplierModel, SupplierDao>
    implements CrudService<SupplierModel, SupplierDao> {

    private final SupplierRepository suppliers;

    /**
     * Instantiates a new Supplier service.
     *
     * @param common    the common service
     * @param suppliers the supplier repository
     */
    @Autowired
    public SupplierService(final CommonService common, final SupplierRepository suppliers) {
        super(common);
        this.suppliers = suppliers;
    }

    @Override
    public CrudRepository<SupplierDao, Long> repository() {
        return suppliers;
    }

    @Override
    public SupplierModel dao2Model(final SupplierDao dao) {
        final SupplierModel model = new SupplierModel();
        model.setId(dao.getId());
        model.setTitle(dao.getTitle());
        model.setDescription(dao.getDescription());
        model.setEmail(dao.getEmail());
        model.setPhoneNumber(dao.getPhoneNumber());
        model.setTaxId(dao.getTaxId());
        model.setTaxAuthority(dao.getTaxAuthority());
        model.setCommon(new ModelCommonProperties(dao.getInsertTimestamp(), dao.getUpdateTimestamp(), dao.getComments(),
            dao.isDeleted(), dao.getDeleteTimestamp()));
        return model;
    }

    @Override
    public SupplierDao model2Dao(final SupplierModel model, SupplierDao dao, final UserDao userDao) {
        if (dao == null) {
            dao = new SupplierDao();
        }
        dao.setOwner(userDao);
        dao.setTitle(model.getTitle());
        dao.setQTitle(GreekLatin.greek2latin(model.getTitle()));
        dao.setDescription(model.getDescription());
        dao.setQDescription(GreekLatin.greek2latin(model.getDescription()));
        dao.setEmail(model.getEmail());
        dao.setTaxId(model.getTaxId());
        dao.setTaxAuthority(model.getTaxAuthority());
        dao.setPhoneNumber(model.getPhoneNumber());
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
        final SupplierDao supplier = retrieveDaoById(id);
        supplier.setDeleted(true);
        supplier.setDeleteTimestamp(new Date());
        repository().save(supplier);
    }

}
