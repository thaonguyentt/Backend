package base.api.book.exception;

public class LeaseCanNotCancel extends RuntimeException {
    String message;
    public LeaseCanNotCancel () {}
    public LeaseCanNotCancel(String message) {
        super(message);
    }
}
