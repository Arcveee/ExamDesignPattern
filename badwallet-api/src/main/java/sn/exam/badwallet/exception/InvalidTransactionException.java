package sn.exam.badwallet.exception;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String reason) {
        super("Invalid transaction: " + reason);
    }
}
