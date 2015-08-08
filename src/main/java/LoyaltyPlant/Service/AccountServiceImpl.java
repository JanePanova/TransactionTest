package LoyaltyPlant.Service;

import LoyaltyPlant.ApplicationException;
import LoyaltyPlant.DAO.AccountDAO;
import LoyaltyPlant.Model.Account;
import LoyaltyPlant.Model.AccountWithWallet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by JaneJava on 5/4/15.
 */
@Service
public class AccountServiceImpl implements AccountService {

    Logger logger = Logger.getLogger(AccountServiceImpl.class);

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    WalletService walletService;

    @Override
    public List<Account> getAll(int start) {
        if (start == -1) {
            start = Integer.MAX_VALUE;
        }
        return accountDAO.getAll(start);
    }

    @Override
    public AccountWithWallet getAccountWithWallet(int id) {
        Account account = accountDAO.get(id);
        if (account == null) {
            throw new ApplicationException("No user with id " + id);
        }
        AccountWithWallet accountWithWallet = new AccountWithWallet(account);
        accountWithWallet.setWallet(walletService.get(id));
        return accountWithWallet;
    }

    @Override
    public Account get(int id) {
        Account account = getBlocked(id);
        if (account.isBlocked()) {
            throw new ApplicationException("No such user" + id);
        }
        return account;
    }

    @Override
    public Account getBlocked(int id) {
        return accountDAO.get(id);
    }

    @Override
    public void put(Account account) {
        checkAccount(account);
        accountDAO.put(account);

    }

    @Override
    public void update(Account account) {
        checkAccount(account);
        accountDAO.update(account);
    }

    @Override
    public void delete(int id) {
        accountDAO.delete(id);
    }

    private void checkAccount(Account account) throws IllegalArgumentException {
        if (account.getName() == null || account.getName().length() == 0) {
            throw new ApplicationException("Name should be not empty");
        }
        if (account.getName().length() < 3 || account.getName().length() > 60) {
            throw new ApplicationException("Name should be from 3 to 60 chars");
        }
        if (account.getName().matches("^.*[^a-zA-Z0-9 ].*$")) {
            throw new ApplicationException("Name should contain only chars and digits");
        }
    }
}
