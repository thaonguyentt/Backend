package base.api.book.exception;

import lombok.experimental.StandardException;

@StandardException
public class VoucherCanNotApply extends RuntimeException {
    String message;
    public VoucherCanNotApply() {}
    public VoucherCanNotApply(String message) {
        super(message);
    }
}
