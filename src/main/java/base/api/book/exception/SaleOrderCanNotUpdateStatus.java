package base.api.book.exception;

public class SaleOrderCanNotUpdateStatus extends RuntimeException {
    String message;
    public SaleOrderCanNotUpdateStatus() {}
    public SaleOrderCanNotUpdateStatus(String message) {
        super(message);
    }
}
