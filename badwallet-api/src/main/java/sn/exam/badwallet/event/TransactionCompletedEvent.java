package sn.exam.badwallet.event;

import org.springframework.context.ApplicationEvent;
import sn.exam.badwallet.entity.Transaction;

public class TransactionCompletedEvent extends ApplicationEvent {

    private final Transaction transaction;

    public TransactionCompletedEvent(Object source, Transaction transaction) {
        super(source);
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
