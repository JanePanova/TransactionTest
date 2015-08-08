package LoyaltyPlant.DAO;

import LoyaltyPlant.Model.Account;

import java.util.List;

/**
 * Created by JaneJava on 5/3/15.
 */
public interface AccountDAO {
    Account get(final int id);
    List<Account> getAll(final int start);
    void put(final Account account);
    void update(final Account account);
    void delete(final Integer id);
}
