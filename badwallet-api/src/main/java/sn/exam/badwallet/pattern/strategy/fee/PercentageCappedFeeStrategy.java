package sn.exam.badwallet.pattern.strategy.fee;

import org.springframework.stereotype.Component;
import sn.exam.badwallet.pattern.strategy.FeeCalculationStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PercentageCappedFeeStrategy implements FeeCalculationStrategy {

    private static final BigDecimal PERCENTAGE = new BigDecimal("0.01");
    private static final BigDecimal CAP = new BigDecimal("5000");

    @Override
    public BigDecimal calculate(BigDecimal amount) {
        BigDecimal fee = amount.multiply(PERCENTAGE).setScale(2, RoundingMode.HALF_UP);
        return fee.compareTo(CAP) > 0 ? CAP : fee;
    }
}
