package sn.exam.badwallet.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import sn.exam.badwallet.entity.Transaction;

@Component
public class TransactionHistoryListener {

    private static final Logger log = LoggerFactory.getLogger(TransactionHistoryListener.class);

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onTransactionCreated(TransactionCreatedEvent event) {
        Transaction tx = event.getTransaction();
        log.info("[AUDIT] type={} amount={} fee={} status={} txId={}",
                tx.getType(), tx.getAmount(), tx.getFee(), tx.getStatus(), tx.getId());
    }
}
