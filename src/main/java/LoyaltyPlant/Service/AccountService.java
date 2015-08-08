package LoyaltyPlant.Service;

import LoyaltyPlant.Model.Account;
import LoyaltyPlant.Model.AccountWithWallet;

import java.util.List;

/**
 * Created by JaneJava on 5/4/15.
 */
public interface AccountService {
    List<Account> getAll(int start);
    AccountWithWallet getAccountWithWallet(int id);
    Account get(int id);
    Account getBlocked(int id);
    void put(Account account);
    void update(Account account);
    void delete(int id);
}
