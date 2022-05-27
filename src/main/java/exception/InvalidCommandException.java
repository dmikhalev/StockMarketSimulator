package exception;

public class InvalidCommandException extends Exception {

    public InvalidCommandException() {
        super("Invalid command. Command must be like 'add <stock ID> B/S <count> <price>' or 'cancel <stock ID> <ID>'");
    }
}
