package sn.exam.badwallet.pattern.strategy;

import sn.exam.badwallet.entity.Transaction;
import sn.exam.badwallet.entity.Wallet;

import java.math.BigDecimal;

public interface DepositStrategy {
    Transaction execute(Wallet wallet, BigDecimal amount);
}
