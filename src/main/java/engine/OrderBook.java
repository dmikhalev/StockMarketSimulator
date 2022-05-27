package engine;

import exception.OrderNotFoundException;
import ledger.TradeLedger;
import utils.ConsoleUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderBook {

    private final Map<Long, Order> orders = new HashMap<>();

    public boolean applyOrder(Order order) throws OrderNotFoundException {
        synchronized (orders) {
            if (order.isAdd()) {
                ConsoleUtils.printAddedOrder(order);
                if (order.isBuy()) {
                    tryBuyExecute(order);
                } else {
                    trySellExecute(order);
                }

                if (order.getCurrentCount() > 0) {
                    orders.put(order.getId(), order);
                }
            } else {
                cancelOrder(order);
            }
            return orders.isEmpty();
        }
    }

    private void tryBuyExecute(Order buyOrder) {
        List<Order> eligibleSellOrders = orders.values()
                .stream()
                .filter(o -> !o.isBuy() && o.getPrice() <= buyOrder.getPrice())
                .sorted(Comparator.comparing(Order::getPrice)
                        .thenComparing(Order::getDate))
                .collect(Collectors.toList());

        execute(buyOrder, eligibleSellOrders);
    }

    private void trySellExecute(Order sellOrder) {
        List<Order> eligibleBuyOrders = orders.values()
                .stream()
                .filter(o -> o.isBuy() && o.getPrice() >= sellOrder.getPrice())
                .sorted(Comparator.comparing(Order::getPrice).reversed()
                        .thenComparing(Order::getDate))
                .collect(Collectors.toList());

        execute(sellOrder, eligibleBuyOrders);
    }

    private void execute(Order order, List<Order> eligibleOrders) {
        for (Order eligibleOrder : eligibleOrders) {
            int count = Math.min(order.getCurrentCount(), eligibleOrder.getCurrentCount());
            order.setCurrentCount(order.getCurrentCount() - count);
            eligibleOrder.setCurrentCount(eligibleOrder.getCurrentCount() - count);

            if (order.isBuy()) {
                addNewExecution(eligibleOrder, order, count);
            } else {
                addNewExecution(order, eligibleOrder, count);
            }
            if (eligibleOrder.getCurrentCount() <= 0) {
                orders.remove(eligibleOrder.getId());
            }
            if (order.getCurrentCount() <= 0) {
                if (orders.get(eligibleOrder.getId()) != null) {
                    orders.get(eligibleOrder.getId()).setCurrentCount(eligibleOrder.getCurrentCount());
                }
                return;
            }
        }
    }

    private void addNewExecution(Order sellOrder, Order buyOrder, int count) {
        TradeLedger.getInstance().addNewExecution(
                buyOrder.getId(), sellOrder.getId(), buyOrder.getStockId(),
                count, sellOrder.getPrice());
    }

    private void cancelOrder(Order order) throws OrderNotFoundException {
        Order target = orders.get(order.getId());
        if (target == null) {
            throw new OrderNotFoundException(order.getId());
        }
        orders.remove(order.getId());
        ConsoleUtils.printCanceledOrder(target);
    }

    public Map<Long, Order> getOrders() {
        return orders;
    }
}
