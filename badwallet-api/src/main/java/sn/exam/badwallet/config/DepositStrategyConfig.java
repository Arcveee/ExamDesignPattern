package sn.exam.badwallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.exam.badwallet.pattern.strategy.DepositStrategy;
import sn.exam.badwallet.pattern.strategy.deposit.CreditCardDepositStrategy;
import sn.exam.badwallet.pattern.strategy.deposit.WalletTargetDepositStrategy;
import sn.exam.shared.enums.DepositMethod;

import java.util.Map;

@Configuration
public class DepositStrategyConfig {

    @Bean
    public Map<DepositMethod, DepositStrategy> depositStrategies(
            CreditCardDepositStrategy creditCard,
            WalletTargetDepositStrategy walletTarget) {
        return Map.of(
                DepositMethod.CREDIT_CARD, creditCard,
                DepositMethod.WALLET_TARGET, walletTarget
        );
    }
}
