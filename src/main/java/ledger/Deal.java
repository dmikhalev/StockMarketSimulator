package ledger;

import java.sql.Timestamp;

public class Deal {

    private long id;
    private String stockId;
    private long buyOrderId;
    private long sellOrderId;
    private int count;
    private int price;
    private Timestamp date;

    public Deal(long id, String stockId, long buyOrderId, long sellOrderId, int count, int price, Timestamp date) {
        this.id = id;
        this.stockId = stockId;
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.count = count;
        this.price = price;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getStockId() {
        return stockId;
    }

    public long getBuyOrderId() {
        return buyOrderId;
    }

    public long getSellOrderId() {
        return sellOrderId;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    public Timestamp getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "id=" + id +
                ", stockId='" + stockId + '\'' +
                ", buyOrderId=" + buyOrderId +
                ", sellOrderId=" + sellOrderId +
                ", count=" + count +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
