package engine;

import exception.OrderNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MatchingEngine {

    private static final MatchingEngine instance = new MatchingEngine();

    public static MatchingEngine getInstance() {
        return instance;
    }

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Map<String, OrderBook> orderBooks = new HashMap<>();


    public void process(Order order) throws OrderNotFoundException {
        if (order.isAdd()) {
            order.setId(idGenerator.incrementAndGet());
        }
        OrderBook orderBook = orderBooks.get(order.getStockId());
        if (orderBook == null) {
            orderBook = new OrderBook();
            orderBooks.put(order.getStockId(), orderBook);
        }
        orderBook.applyOrder(order);
    }

    public Map<String, OrderBook> getOrderBooks() {
        return orderBooks;
    }
}
