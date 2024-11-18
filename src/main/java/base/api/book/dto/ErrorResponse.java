package base.api.book.dto;

import lombok.Getter;

@Getter
public class ErrorResponse {
    // Getter
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

}
