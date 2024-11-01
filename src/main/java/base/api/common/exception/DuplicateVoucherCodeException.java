package base.api.common.exception;

public class DuplicateVoucherCodeException extends RuntimeException {
    public DuplicateVoucherCodeException(String message) {
        super(message);
    }
}