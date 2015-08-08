package LoyaltyPlant.Model;

/**
 * Created by JaneJava on 5/3/15.
 */
public class Transaction {
    private int id;
    private int from;
    private int to;
    private String nameFrom;
    private String nameTo;
    private double money;
    private boolean blocked;
    private long timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getNameFrom() {
        return nameFrom;
    }

    public void setNameFrom(String nameFrom) {
        this.nameFrom = nameFrom;
    }

    public String getNameTo() {
        return nameTo;
    }

    public void setNameTo(String nameTo) {
        this.nameTo = nameTo;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Transaction() {
        this.timestamp = System.currentTimeMillis();
    }

    public Transaction(int from, int to, double money) {
        this.from = from;
        this.to = to;
        this.money = money;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", nameFrom='" + nameFrom + '\'' +
                ", nameTo='" + nameTo + '\'' +
                ", money=" + money +
                ", blocked=" + blocked +
                ", timestamp=" + timestamp +
                '}';
    }
}
