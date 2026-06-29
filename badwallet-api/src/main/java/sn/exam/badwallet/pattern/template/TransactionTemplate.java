package sn.exam.badwallet.pattern.template;

import org.springframework.context.ApplicationEventPublisher;
import sn.exam.badwallet.entity.Transaction;
import sn.exam.badwallet.entity.Wallet;
import sn.exam.badwallet.event.TransactionCompletedEvent;
import sn.exam.badwallet.repository.TransactionRepository;
import sn.exam.shared.enums.TransactionStatus;

import java.math.BigDecimal;

public abstract class TransactionTemplate {

    private final TransactionRepository transactionRepository;
    private final ApplicationEventPublisher eventPublisher;

    protected TransactionTemplate(TransactionRepository transactionRepository,
                                   ApplicationEventPublisher eventPublisher) {
        this.transactionRepository = transactionRepository;
        this.eventPublisher = eventPublisher;
    }

    public final Transaction process(Wallet source, Wallet target, BigDecimal amount) {
        validate(source, target, amount);
        Transaction transaction = buildTransaction(source, target, amount);
        executeTransfer(source, target, amount);
        transaction.setStatus(TransactionStatus.SUCCESS);
        Transaction saved = transactionRepository.save(transaction);
        eventPublisher.publishEvent(new TransactionCompletedEvent(this, saved));
        return saved;
    }

    protected abstract void validate(Wallet source, Wallet target, BigDecimal amount);

    protected abstract Transaction buildTransaction(Wallet source, Wallet target, BigDecimal amount);

    protected abstract void executeTransfer(Wallet source, Wallet target, BigDecimal amount);
}
