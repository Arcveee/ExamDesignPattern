package sn.exam.badwallet.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.exam.badwallet.dto.DepositRequest;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.entity.Transaction;
import sn.exam.badwallet.entity.Wallet;
import sn.exam.badwallet.exception.WalletNotFoundException;
import sn.exam.badwallet.mapper.TransactionMapper;
import sn.exam.badwallet.pattern.strategy.DepositStrategy;
import sn.exam.badwallet.pattern.strategy.DepositStrategyFactory;
import sn.exam.badwallet.pattern.template.AbstractTransactionProcessor;
import sn.exam.badwallet.repository.TransactionRepository;
import sn.exam.badwallet.repository.WalletRepository;
import sn.exam.shared.enums.TransactionStatus;
import sn.exam.shared.enums.TransactionType;

import java.math.BigDecimal;

@Service
public class DepositService extends AbstractTransactionProcessor {

    private final DepositStrategyFactory strategyFactory;
    private DepositStrategy currentStrategy;

    public DepositService(WalletRepository walletRepository,
                          TransactionRepository transactionRepository,
                          TransactionMapper transactionMapper,
                          ApplicationEventPublisher eventPublisher,
                          DepositStrategyFactory strategyFactory) {
        super(walletRepository, transactionRepository, transactionMapper, eventPublisher);
        this.strategyFactory = strategyFactory;
    }

    @Transactional
    public TransactionResponse deposit(Long walletId, DepositRequest request) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(String.valueOf(walletId)));
        currentStrategy = strategyFactory.resolve(request.paymentMethod());
        return process(wallet, request.amount());
    }

    @Override
    protected void validate(Wallet wallet, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }

    @Override
    protected Transaction execute(Wallet wallet, BigDecimal amount) {
        BigDecimal credited = currentStrategy.process(wallet, amount);
        BigDecimal fee = amount.subtract(credited);
        wallet.setBalance(wallet.getBalance().add(credited));
        return Transaction.builder()
                .type(TransactionType.DEPOSIT)
                .amount(amount)
                .fee(fee)
                .status(TransactionStatus.SUCCESS)
                .targetWallet(wallet)
                .build();
    }
}
