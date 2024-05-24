package base.api.book.exception;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@StandardException
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No such listing exists.")
public class NoSuchListingException extends RuntimeException {
}
