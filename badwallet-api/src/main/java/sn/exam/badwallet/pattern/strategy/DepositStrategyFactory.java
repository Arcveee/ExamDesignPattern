package sn.exam.badwallet.pattern.strategy;

import org.springframework.stereotype.Component;
import sn.exam.shared.enums.DepositMethod;

import java.util.Map;

@Component
public class DepositStrategyFactory {

    private final Map<DepositMethod, DepositStrategy> strategies;

    public DepositStrategyFactory(Map<DepositMethod, DepositStrategy> strategies) {
        this.strategies = strategies;
    }

    public DepositStrategy resolve(DepositMethod method) {
        DepositStrategy strategy = strategies.get(method);
        if (strategy == null) {
            throw new IllegalArgumentException("No deposit strategy for: " + method);
        }
        return strategy;
    }
}
