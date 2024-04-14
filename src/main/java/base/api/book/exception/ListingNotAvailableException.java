package base.api.book.exception;


public class ListingNotAvailableException extends RuntimeException {
    String message;

    public ListingNotAvailableException() {}
    public ListingNotAvailableException(String message) {
        super(message);
    }
}
