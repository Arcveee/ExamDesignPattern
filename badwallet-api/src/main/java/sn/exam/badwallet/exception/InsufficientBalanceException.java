package sn.exam.badwallet.exception;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(BigDecimal available, BigDecimal required) {
        super("Insufficient balance: available=" + available + ", required=" + required);
    }
}
