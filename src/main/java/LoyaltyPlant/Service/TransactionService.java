package LoyaltyPlant.Service;

import LoyaltyPlant.Model.Transaction;

import java.util.List;

/**
 * Created by JaneJava on 5/4/15.
 */
public interface TransactionService {
    void addMoney(int id, double money);
    boolean withdrawMoney(int id, double money);
    List<Transaction> getAll(int start);
    void makeTransaction(int from, int to, double money);
}
