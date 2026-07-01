package sn.exam.badwallet.pattern.strategy;

import sn.exam.badwallet.entity.Wallet;

import java.math.BigDecimal;

public interface DepositStrategy {
    BigDecimal process(Wallet wallet, BigDecimal amount);
}
