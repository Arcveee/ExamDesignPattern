package sn.exam.badwallet.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.entity.Transaction;
import sn.exam.badwallet.entity.Wallet;
import sn.exam.badwallet.exception.InsufficientBalanceException;
import sn.exam.badwallet.exception.InvalidTransactionException;
import sn.exam.badwallet.mapper.TransactionMapper;
import sn.exam.badwallet.pattern.template.AbstractTransactionProcessor;
import sn.exam.badwallet.repository.TransactionRepository;
import sn.exam.badwallet.repository.WalletRepository;
import sn.exam.shared.enums.TransactionStatus;
import sn.exam.shared.enums.TransactionType;

import java.math.BigDecimal;

@Service
public class BillPaymentService extends AbstractTransactionProcessor {

    public BillPaymentService(WalletRepository walletRepository,
                               TransactionRepository transactionRepository,
                               TransactionMapper transactionMapper,
                               ApplicationEventPublisher eventPublisher) {
        super(walletRepository, transactionRepository, transactionMapper, eventPublisher);
    }

    @Transactional
    public TransactionResponse payBill(Wallet wallet, BigDecimal amount, String billReference) {
        if (billReference == null || billReference.isBlank()) {
            throw new InvalidTransactionException("billReference is required");
        }
        return process(wallet, amount);
    }

    @Override
    protected void validate(Wallet wallet, BigDecimal amount) {
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(wallet.getBalance(), amount);
        }
    }

    @Override
    protected Transaction execute(Wallet wallet, BigDecimal amount) {
        wallet.setBalance(wallet.getBalance().subtract(amount));
        return Transaction.builder()
                .type(TransactionType.BILL_PAYMENT)
                .amount(amount)
                .fee(BigDecimal.ZERO)
                .status(TransactionStatus.SUCCESS)
                .sourceWallet(wallet)
                .build();
    }
}
