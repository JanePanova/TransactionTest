package LoyaltyPlant.Model;

/**
 * Created by JaneJava on 5/3/15.
 */
public class AccountWithWallet extends Account {
    private Wallet wallet;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public AccountWithWallet() {
        super();
    }

    public AccountWithWallet(Account account) {
        this.setId(account.getId());
        this.setName(account.getName());
        this.setBlocked(account.isBlocked());
    }
}
