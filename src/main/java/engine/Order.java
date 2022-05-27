package engine;

import java.sql.Timestamp;

public class Order {

    private long id;
    private String stockId;
    private boolean isAdd;
    private boolean isBuy;
    private int initialCount;
    private int currentCount;
    private int price;
    private Timestamp date;

    public Order() {
    }

    public Order(long id, boolean isAdd) {
        this.id = id;
        this.isAdd = isAdd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public int getInitialCount() {
        return initialCount;
    }

    public void setInitialCount(int initialCount) {
        this.initialCount = initialCount;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", stockId='" + stockId + '\'' +
                ", isAdd=" + isAdd +
                ", isBuy=" + isBuy +
                ", initialCount=" + initialCount +
                ", currentCount=" + currentCount +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
