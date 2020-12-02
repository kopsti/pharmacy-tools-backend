package gr.appleton.ms.pharmacytools.models.accounts;

import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.crud.AbstractServiceCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.crud.MyCrudRepository;
import gr.appleton.ms.pharmacytools.common.dto.ModelCommonProperties;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.common.utils.CommonService;
import gr.appleton.ms.pharmacytools.common.utils.GreekLatin;
import gr.appleton.ms.pharmacytools.models.accounts.dto.BankAccountDao;
import gr.appleton.ms.pharmacytools.models.accounts.dto.BankAccountModel;
import gr.appleton.ms.pharmacytools.models.banks.BankService;
import gr.appleton.ms.pharmacytools.models.suppliers.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * The service responsible to handle operations for Bank Account instances management.
 */
@Service
@Slf4j
public class BankAccountService extends AbstractServiceCrud<BankAccountModel, BankAccountDao>
    implements CrudService<BankAccountModel, BankAccountDao> {

    private final BankAccountRepository bankAccounts;
    private final BankService banks;
    private final SupplierService suppliers;

    /**
     * Instantiates a new Bank Account service.
     *
     * @param common       the common service
     * @param bankAccounts the bank account service
     * @param banks        the banks service
     * @param suppliers    the suppliers service
     */
    @Autowired
    public BankAccountService(final CommonService common, final BankAccountRepository bankAccounts,
                              final BankService banks, final SupplierService suppliers) {
        super(common);
        this.bankAccounts = bankAccounts;
        this.banks = banks;
        this.suppliers = suppliers;
    }

    @Override
    public MyCrudRepository<BankAccountDao, Long> repository() {
        return bankAccounts;
    }

    @Override
    public BankAccountModel dao2Model(final BankAccountDao dao) {
        final BankAccountModel model = new BankAccountModel();
        model.setId(dao.getId());
        try {
            model.setBank(banks.retrieveById(dao.getBank().getId()));
        } catch (final GenericException e) {
            log.error(e.getMessage(), e);
        }
        try {
            model.setSupplier(suppliers.retrieveById(dao.getSupplier().getId()));
        } catch (final GenericException e) {
            log.error(e.getMessage(), e);
        }
        model.setIban(dao.getIban());
        model.setNumber(dao.getNumber());
        model.setCommon(new ModelCommonProperties(dao.getInsertTimestamp(), dao.getUpdateTimestamp(), dao.getComments(),
            dao.isDeleted(), dao.getDeleteTimestamp()));
        return model;
    }

    @Override
    public BankAccountDao model2Dao(final BankAccountModel model, BankAccountDao dao, final UserDao userDao)
        throws GenericException {
        if (dao == null) {
            dao = new BankAccountDao();
        }

        dao.setIban(model.getIban());
        dao.setNumber(model.getNumber());
        dao.setOwner(userDao);
        dao.setBank(banks.retrieveDaoById(model.getBankId()));
        dao.setSupplier(suppliers.retrieveDaoById(model.getSupplierId()));
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
        final BankAccountDao bankAccount = retrieveDaoById(id);
        bankAccount.setDeleted(true);
        bankAccount.setDeleteTimestamp(new Date());
        repository().save(bankAccount);
    }

}
