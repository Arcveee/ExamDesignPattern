package sn.exam.badwallet.pattern.strategy.deposit;

import org.springframework.stereotype.Component;
import sn.exam.badwallet.entity.Wallet;
import sn.exam.badwallet.pattern.strategy.DepositStrategy;
import sn.exam.shared.enums.DepositMethod;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component("WALLET_TARGET")
public class WalletTargetDepositStrategy implements DepositStrategy {

    private static final BigDecimal FEE_RATE = new BigDecimal("0.005");

    @Override
    public BigDecimal process(Wallet wallet, BigDecimal amount) {
        BigDecimal fee = amount.multiply(FEE_RATE).setScale(2, RoundingMode.HALF_UP);
        return amount.subtract(fee);
    }

    public DepositMethod getMethod() {
        return DepositMethod.WALLET_TARGET;
    }
}
