package exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String s) {
        super(s);
    }
}
