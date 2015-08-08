package LoyaltyPlant.Service;

import LoyaltyPlant.DAO.TransactionDAO;
import LoyaltyPlant.DAO.WalletDAO;
import LoyaltyPlant.Model.Transaction;
import LoyaltyPlant.Model.Wallet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by JaneJava on 5/4/15.
 */
@Service
public class WalletServiceImpl implements WalletService{

    Logger logger = Logger.getLogger(WalletServiceImpl.class);

    @Autowired
    WalletDAO walletDAO;

    @Autowired
    TransactionDAO transactionDAO;

    @Override
    public Wallet get(int id) {
        Wallet wallet = walletDAO.get(id);
        if (wallet == null) {
            put(id);
            wallet = new Wallet(id);
        }
        List<Transaction> newTransactions = transactionDAO.getTransactionsForUser(id, wallet.getLastUpdate());
        double money = wallet.getMoney();
        long lastUpdate = wallet.getLastUpdate();
        for (Transaction transaction : newTransactions) {
            if (transaction.getTo() == id) {
                money += transaction.getMoney();
            } else {
                if (money - transaction.getMoney() < 0) {
                    logger.error("SUCH MUCH");
                    transaction.setBlocked(true);
                }
                money -= transaction.getMoney();
            }
            lastUpdate = Math.max(lastUpdate, transaction.getTimestamp());
        }
        wallet.setMoney(money);
        wallet.setLastUpdate(lastUpdate);
        update(wallet);
        return wallet;
    }

    @Override
    public void update(Wallet wallet) {
        walletDAO.update(wallet);
    }

    @Override
    public void put(int id) {
        walletDAO.put(id);
    }
}
