package sn.exam.badwallet.pattern.strategy.deposit;

import org.springframework.stereotype.Component;
import sn.exam.badwallet.entity.Wallet;
import sn.exam.badwallet.pattern.strategy.DepositStrategy;
import sn.exam.shared.enums.DepositMethod;

import java.math.BigDecimal;

@Component("CREDIT_CARD")
public class CreditCardDepositStrategy implements DepositStrategy {

    @Override
    public BigDecimal process(Wallet wallet, BigDecimal amount) {
        return amount;
    }

    public DepositMethod getMethod() {
        return DepositMethod.CREDIT_CARD;
    }
}
