package sn.exam.badwallet.pattern.strategy;

import java.math.BigDecimal;

public interface FeeCalculationStrategy {
    BigDecimal calculate(BigDecimal amount);
}
