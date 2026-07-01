package sn.exam.badwallet.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.dto.WithdrawRequest;
import sn.exam.badwallet.entity.Transaction;
import sn.exam.badwallet.entity.Wallet;
import sn.exam.badwallet.exception.InsufficientBalanceException;
import sn.exam.badwallet.exception.WalletNotFoundException;
import sn.exam.badwallet.mapper.TransactionMapper;
import sn.exam.badwallet.pattern.strategy.FeeCalculationStrategy;
import sn.exam.badwallet.pattern.template.AbstractTransactionProcessor;
import sn.exam.badwallet.repository.TransactionRepository;
import sn.exam.badwallet.repository.WalletRepository;
import sn.exam.shared.enums.TransactionStatus;
import sn.exam.shared.enums.TransactionType;

import java.math.BigDecimal;

@Service
public class WithdrawService extends AbstractTransactionProcessor {

    private final FeeCalculationStrategy feeCalculationStrategy;

    public WithdrawService(WalletRepository walletRepository,
                           TransactionRepository transactionRepository,
                           TransactionMapper transactionMapper,
                           ApplicationEventPublisher eventPublisher,
                           FeeCalculationStrategy feeCalculationStrategy) {
        super(walletRepository, transactionRepository, transactionMapper, eventPublisher);
        this.feeCalculationStrategy = feeCalculationStrategy;
    }

    @Transactional
    public TransactionResponse withdraw(WithdrawRequest request) {
        Wallet wallet = walletRepository.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new WalletNotFoundException(request.phoneNumber()));
        return process(wallet, request.amount());
    }

    @Override
    protected void validate(Wallet wallet, BigDecimal amount) {
        BigDecimal fee = feeCalculationStrategy.calculate(amount);
        BigDecimal totalRequired = amount.add(fee);
        if (wallet.getBalance().compareTo(totalRequired) < 0) {
            throw new InsufficientBalanceException(wallet.getBalance(), totalRequired);
        }
    }

    @Override
    protected Transaction execute(Wallet wallet, BigDecimal amount) {
        BigDecimal fee = feeCalculationStrategy.calculate(amount);
        wallet.setBalance(wallet.getBalance().subtract(amount).subtract(fee));
        return Transaction.builder()
                .type(TransactionType.WITHDRAWAL)
                .amount(amount)
                .fee(fee)
                .status(TransactionStatus.SUCCESS)
                .sourceWallet(wallet)
                .build();
    }
}
