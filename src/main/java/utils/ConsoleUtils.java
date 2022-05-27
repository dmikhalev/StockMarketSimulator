package utils;

import engine.MatchingEngine;
import engine.Order;
import engine.OrderBook;
import ledger.Deal;
import ledger.TradeLedger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUtils {

    private static final String TIMESTAMP_PATTERN = "dd.MM.yyyy HH:mm:ss";
    private static final String ORDER_ADDED_MESSAGE = "[%s] Order with ID %d added: %s %s %d @ %d\n";
    private static final String ORDER_CANCELED_MESSAGE = "[%s] Order with ID %d canceled: %s %s %d @ %d\n";
    private static final String NEW_EXECUTION_MESSAGE = "[%s] New execution with ID %d: %s %d @ %d (orders %d and %d)\n";

    public static String readCommand(Scanner scanner) {
        System.out.print("> ");
        return scanner.nextLine();
    }

    public static void printError(String message) {
        System.err.println(message);
    }

    public static void printAddedOrder(Order order) {
        String timestamp = new SimpleDateFormat(TIMESTAMP_PATTERN)
                .format(new Timestamp(System.currentTimeMillis()));
        System.out.printf(ORDER_ADDED_MESSAGE, timestamp,
                order.getId(), order.getStockId(),
                order.isBuy() ? "Buy" : "Sell",
                order.getInitialCount(), order.getPrice());
    }

    public static void printCanceledOrder(Order order) {
        String timestamp = new SimpleDateFormat(TIMESTAMP_PATTERN)
                .format(new Timestamp(System.currentTimeMillis()));
        System.out.printf(ORDER_CANCELED_MESSAGE, timestamp,
                order.getId(), order.getStockId(),
                order.isBuy() ? "Buy" : "Sell",
                order.getInitialCount(), order.getPrice());
    }

    public static void printNewExecution(Deal deal) {
        String timestamp = new SimpleDateFormat(TIMESTAMP_PATTERN)
                .format(deal.getDate());
        System.out.printf(NEW_EXECUTION_MESSAGE,
                timestamp, deal.getId(), deal.getStockId(), deal.getCount(),
                deal.getPrice(), deal.getBuyOrderId(), deal.getSellOrderId());
    }

    public static void showTradeLedger() {
        TradeLedger.getInstance().getDeals().forEach(System.out::println);
    }

    public static void showOrderBooks() {
        Map<String, OrderBook> orderBooks = MatchingEngine.getInstance().getOrderBooks();
        for (Map.Entry<String, OrderBook> idToOB : orderBooks.entrySet()) {
            System.out.println(idToOB.getKey() + ":");
            Map<Long, Order> orders = idToOB.getValue().getOrders();
            for (Map.Entry<Long, Order> order : orders.entrySet()) {
                System.out.println(order.getValue());
            }
        }
    }

}
