package gateway;

import engine.Order;
import exception.InvalidCommandException;

import java.sql.Timestamp;

public class CommandParser {
    private static final String ADD = "add";
    private static final String CANCEL = "cancel";
    private static final String STOCK_ID_REG_EXP = "^[a-zA-Z]+$";
    private static final String B = "b"; // buy
    private static final String S = "s"; // sell


    public static Order parseCommand(String command, long currentTimeMillis) throws InvalidCommandException {
        Order order = null;
        String[] parts = command.split(" ");
        if (parts.length == 5 && ADD.equalsIgnoreCase(parts[0])) {
            order = parseAddCommand(parts);
        } else if (parts.length == 3 && CANCEL.equalsIgnoreCase(parts[0])) {
            try {
                long id = Long.parseLong(parts[2]);
                if (id > 0) {
                    order = new Order(id, false);
                    validateAndSetStockId(parts[1], order);
                }
            } catch (Exception ignored) {
            }
        }
        if (order == null) {
            throw new InvalidCommandException();
        }
        order.setDate(new Timestamp(currentTimeMillis));
        return order;
    }

    private static Order parseAddCommand(String[] parts) throws InvalidCommandException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Order order = new Order();
        order.setAdd(true);

        validateAndSetStockId(parts[1], order);

        if (B.equalsIgnoreCase(parts[2])) {
            order.setBuy(true);
        } else if (!S.equalsIgnoreCase(parts[2])) {
            throw new InvalidCommandException();
        }

        int count = getPositiveInt(parts[3]);
        order.setInitialCount(count);
        order.setCurrentCount(count);

        int price = getPositiveInt(parts[4]);
        order.setPrice(price);

        order.setDate(timestamp);
        return order;
    }

    private static void validateAndSetStockId(String stockId, Order order) throws InvalidCommandException {
        if (stockId.matches(STOCK_ID_REG_EXP)) {
            order.setStockId(stockId.toUpperCase());
        } else {
            throw new InvalidCommandException();
        }
    }

    private static int getPositiveInt(String part) throws InvalidCommandException {
        try {
            int id = Integer.parseInt(part);
            if (id > 0) {
                return id;
            }
        } catch (Exception ignored) {
        }
        throw new InvalidCommandException();
    }
}
