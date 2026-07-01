package sn.exam.badwallet.exception;

public class WalletAlreadyExistsException extends RuntimeException {
    public WalletAlreadyExistsException(String field, String value) {
        super("Wallet already exists with " + field + ": " + value);
    }
}
