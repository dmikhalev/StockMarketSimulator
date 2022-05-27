package ledger;

import utils.ConsoleUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class TradeLedger {

    private static final TradeLedger instance = new TradeLedger();

    public static TradeLedger getInstance() {
        return instance;
    }

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final List<Deal> deals = new ArrayList<>();

    public void addNewExecution(long buyOrderId, long sellOrderId, String stockId, int count, int price) {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        Deal deal = new Deal(idGenerator.incrementAndGet(), stockId, buyOrderId, sellOrderId, count, price, date);
        deals.add(deal);
        ConsoleUtils.printNewExecution(deal);
    }

    public List<Deal> getDeals() {
        return deals;
    }
}
