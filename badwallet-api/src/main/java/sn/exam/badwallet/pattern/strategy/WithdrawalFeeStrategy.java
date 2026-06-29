package sn.exam.badwallet.pattern.strategy;

import java.math.BigDecimal;

public interface WithdrawalFeeStrategy {
    BigDecimal calculateFee(BigDecimal amount);
}
