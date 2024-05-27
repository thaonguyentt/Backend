package base.api.book.exception;

import lombok.experimental.StandardException;

@StandardException
public class ListingNotAvailableException extends RuntimeException {
    String message;

    public ListingNotAvailableException() {}
    public ListingNotAvailableException(String message) {
        super(message);
    }
}
