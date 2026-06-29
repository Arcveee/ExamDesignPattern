package sn.exam.badwallet.exception;

public class ExternalServiceUnavailableException extends RuntimeException {
    public ExternalServiceUnavailableException(String service, Throwable cause) {
        super("External service unavailable: " + service, cause);
    }
}
