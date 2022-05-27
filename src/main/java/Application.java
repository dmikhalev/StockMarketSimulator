import exception.InvalidCommandException;
import exception.OrderNotFoundException;
import gateway.TradingGateway;
import utils.ConsoleUtils;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        System.out.println("0 - exit, 1 - show trade ledger, 2 - show order books");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String command = ConsoleUtils.readCommand(scanner);
                if ("0".equals(command)) {
                    return;
                }
                if ("1".equals(command)) {
                    ConsoleUtils.showTradeLedger();
                    continue;
                }
                if ("2".equals(command)) {
                    ConsoleUtils.showOrderBooks();
                    continue;
                }
                try {
                    TradingGateway.getInstance().processCommand(command);
                } catch (InvalidCommandException | OrderNotFoundException e) {
                    ConsoleUtils.printError(e.getMessage());
                }
            }
        }
    }

}
