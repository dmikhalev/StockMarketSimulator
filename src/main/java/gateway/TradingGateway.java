package gateway;

import engine.MatchingEngine;
import engine.Order;
import exception.InvalidCommandException;
import exception.OrderNotFoundException;

public class TradingGateway {

    private static final TradingGateway instance = new TradingGateway();

    public static TradingGateway getInstance() {
        return instance;
    }

    public void processCommand(String command) throws InvalidCommandException, OrderNotFoundException {
        Order order = CommandParser.parseCommand(command, System.currentTimeMillis());
        MatchingEngine.getInstance().process(order);
    }
}
