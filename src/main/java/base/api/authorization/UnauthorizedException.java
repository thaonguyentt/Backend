package base.api.authorization;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {}

    public UnauthorizedException(String message) {
        super(message);
    }
}
