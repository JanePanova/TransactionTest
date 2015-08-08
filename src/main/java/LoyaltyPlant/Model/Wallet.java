package LoyaltyPlant.Model;

/**
 * Created by JaneJava on 5/3/15.
 */
public class Wallet {
    private int id;
    private double money;
    private long lastUpdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Wallet() {
    }

    public Wallet(int id) {
        this.id = id;
        this.money = 0;
        this.lastUpdate = 0;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", money=" + money +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
