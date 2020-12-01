package gr.appleton.ms.pharmacytools.models.banks;

import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.crud.AbstractServiceCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.utils.CommonService;
import gr.appleton.ms.pharmacytools.models.banks.dto.BankDao;
import gr.appleton.ms.pharmacytools.models.banks.dto.BankModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * The service responsible to handle operations for Bank instances management.
 */
@Service
@Slf4j
public class BankService extends AbstractServiceCrud<BankModel, BankDao>
    implements CrudService<BankModel, BankDao> {

    private final BankRepository banks;

    /**
     * Instantiates a new Bank service.
     *
     * @param banks  the banks repository
     * @param common the common service
     */
    @Autowired
    public BankService(final BankRepository banks, final CommonService common) {
        super(common);
        this.banks = banks;
    }

    @Override
    public CrudRepository<BankDao, Long> repository() {
        return banks;
    }

    @Override
    public BankModel dao2Model(final BankDao dao) {
        final BankModel model = new BankModel();
        model.setId(dao.getId());
        model.setTitle(dao.getTitle());
        return model;
    }

    @Override
    public BankDao model2Dao(final BankModel model, BankDao dao, final UserDao userDao) {
        if (dao == null) {
            dao = new BankDao();
        }
        dao.setTitle(model.getTitle());
        return dao;
    }

}
