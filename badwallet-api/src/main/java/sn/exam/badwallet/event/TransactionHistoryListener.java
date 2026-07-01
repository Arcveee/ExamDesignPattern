package sn.exam.badwallet.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionHistoryListener implements ApplicationListener<TransactionCompletedEvent> {

    @Override
    public void onApplicationEvent(TransactionCompletedEvent event) {
        // À implémenter : historisation, notification, audit log
    }
}
