package LoyaltyPlant.Service;

import LoyaltyPlant.ApplicationException;
import LoyaltyPlant.DAO.AccountDAO;
import LoyaltyPlant.DAO.TransactionDAO;
import LoyaltyPlant.DAO.WalletDAO;
import LoyaltyPlant.Model.Account;
import LoyaltyPlant.Model.Transaction;
import LoyaltyPlant.Model.Wallet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * Created by JaneJava on 5/4/15.
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    private static final Logger logger = Logger.getLogger(TransactionServiceImpl.class);

    @Autowired
    TransactionDAO transactionDAO;

    @Autowired
    AccountService accountService;

    @Autowired
    WalletService walletService;

    @Override
    public void addMoney(int id, double money) {
        Account account = accountService.get(id);
        if (account == null) {
            throw new ApplicationException("No user with id " + id);
        }
        money = fixMoney(money);
        Transaction transaction = new Transaction(0, id, money);
        transactionDAO.put(transaction);
    }

    @Override
    public boolean withdrawMoney(int id, double money) {
        Account account = accountService.get(id);
        if (account == null) {
            throw new ApplicationException("No user with id " + id);
        }
        money = fixMoney(money);
        Wallet wallet = walletService.get(id);
        if (wallet.getMoney() < money) {
            throw new ApplicationException("Not enough money");
        }
        Transaction transaction = new Transaction(id, 0, money);
        transactionDAO.put(transaction);
        return true;
    }

    @Override
    public List<Transaction> getAll(int start) {
        if (start == -1) {
            start = Integer.MAX_VALUE;
        }
        List<Transaction> list = transactionDAO.getAll(start);
        Map<Integer, Account> map = new HashMap<>();
        for (Transaction transaction : list) {
            map.put(transaction.getFrom(), null);
            map.put(transaction.getTo(), null);
        }
        for (Map.Entry<Integer, Account> entry : map.entrySet()) {
            if (entry.getKey() == 0) {
                entry.setValue(new Account(0, "SU"));
            } else {
                entry.setValue(accountService.getBlocked(entry.getKey()));
            }
        }
        logger.info(map.toString());
        for (Transaction transaction : list) {
            transaction.setNameFrom(map.get(transaction.getFrom()).getName());
            transaction.setNameTo(map.get(transaction.getTo()).getName());
        }
        logger.info(list);
        return list;
    }

    @Override
    public void makeTransaction(int from, int to, double money) {
        Account fromAccount = accountService.get(from);
        if (fromAccount == null) {
            throw new ApplicationException("No user with id " + from);
        }
        Account toAccount = accountService.get(to);
        if (toAccount == null) {
            throw new ApplicationException("No user with id " + to);
        }
        Wallet fromWallet = walletService.get(from);
        if (fromWallet.getMoney() - money < 0) {
            throw new ApplicationException("Not enough money");
        }
        money = fixMoney(money);
        Transaction transaction = new Transaction(from, to, money);
        transactionDAO.put(transaction);
    }

    private double fixMoney(double money) {
        return Math.floor(money * 100) / 100;
    }
}
