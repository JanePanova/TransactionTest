package LoyaltyPlant.DAO;

import LoyaltyPlant.Model.Wallet;

/**
 * Created by JaneJava on 5/3/15.
 */
public interface WalletDAO {
    Wallet get(int id);
    void put(int id);
    void update(Wallet wallet);
}
