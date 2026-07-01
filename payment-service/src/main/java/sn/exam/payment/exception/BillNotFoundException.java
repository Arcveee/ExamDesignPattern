package sn.exam.payment.exception;

public class BillNotFoundException extends RuntimeException {
    public BillNotFoundException(String provider, String reference) {
        super("Bill not found: provider=" + provider + ", reference=" + reference);
    }
}
