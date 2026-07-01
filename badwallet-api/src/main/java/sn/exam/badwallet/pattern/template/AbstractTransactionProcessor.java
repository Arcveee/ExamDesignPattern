package sn.exam.badwallet.pattern.template;

import org.springframework.context.ApplicationEventPublisher;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.entity.Transaction;
import sn.exam.badwallet.entity.Wallet;
import sn.exam.badwallet.event.TransactionCreatedEvent;
import sn.exam.badwallet.mapper.TransactionMapper;
import sn.exam.badwallet.repository.TransactionRepository;
import sn.exam.badwallet.repository.WalletRepository;

import java.math.BigDecimal;

public abstract class AbstractTransactionProcessor {

    protected final WalletRepository walletRepository;
    protected final TransactionRepository transactionRepository;
    protected final TransactionMapper transactionMapper;
    private final ApplicationEventPublisher eventPublisher;

    protected AbstractTransactionProcessor(WalletRepository walletRepository,
                                           TransactionRepository transactionRepository,
                                           TransactionMapper transactionMapper,
                                           ApplicationEventPublisher eventPublisher) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.eventPublisher = eventPublisher;
    }

    public final TransactionResponse process(Wallet wallet, BigDecimal amount) {
        validate(wallet, amount);
        Transaction transaction = execute(wallet, amount);
        Transaction saved = persist(transaction, wallet);
        publishEvent(saved);
        return transactionMapper.toResponse(saved);
    }

    public final TransactionResponse process(Wallet source, Wallet target, BigDecimal amount) {
        validateTransfer(source, target, amount);
        Transaction transaction = executeTransfer(source, target, amount);
        walletRepository.save(source);
        walletRepository.save(target);
        Transaction saved = transactionRepository.save(transaction);
        publishEvent(saved);
        return transactionMapper.toResponse(saved);
    }

    protected abstract void validate(Wallet wallet, BigDecimal amount);

    protected abstract Transaction execute(Wallet wallet, BigDecimal amount);

    protected void validateTransfer(Wallet source, Wallet target, BigDecimal amount) {
        throw new UnsupportedOperationException("validateTransfer not implemented");
    }

    protected Transaction executeTransfer(Wallet source, Wallet target, BigDecimal amount) {
        throw new UnsupportedOperationException("executeTransfer not implemented");
    }

    private Transaction persist(Transaction transaction, Wallet wallet) {
        walletRepository.save(wallet);
        return transactionRepository.save(transaction);
    }

    private void publishEvent(Transaction transaction) {
        eventPublisher.publishEvent(new TransactionCreatedEvent(this, transaction));
    }
}
