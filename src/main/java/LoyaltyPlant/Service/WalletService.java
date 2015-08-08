package LoyaltyPlant.Service;

import LoyaltyPlant.Model.Wallet;

/**
 * Created by JaneJava on 5/4/15.
 */
public interface WalletService {
    Wallet get(int id);
    void update(Wallet wallet);
    void put(int id);
}
