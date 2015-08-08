package LoyaltyPlant.DAO;

import LoyaltyPlant.Model.Transaction;

import java.util.List;

/**
 * Created by JaneJava on 5/4/15.
 */
public interface TransactionDAO {
    void put(Transaction transaction);
    void update(Transaction transaction);
    Transaction get(int id);
    List<Transaction> getAll(int start);
    List<Transaction> getFromUser(int id, long from);
    List<Transaction> getToUser(int id, long to);
    List<Transaction> getTransactionsForUser(int id, long from);
}
