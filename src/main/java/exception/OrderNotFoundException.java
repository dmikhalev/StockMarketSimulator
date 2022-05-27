package exception;

public class OrderNotFoundException extends Exception {

    public OrderNotFoundException(long id) {
        super(String.format("Order with ID %d not found", id));
    }
}
