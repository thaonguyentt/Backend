package base.api.book.exception;

import lombok.experimental.StandardException;

@StandardException
public class SaleOrderCanNotUpdateStatus extends RuntimeException {
    String message;
    public SaleOrderCanNotUpdateStatus() {}
    public SaleOrderCanNotUpdateStatus(String message) {
        super(message);
    }
}
