package sn.exam.badwallet.mapper;

import org.springframework.stereotype.Component;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.entity.Transaction;
import sn.exam.badwallet.entity.Wallet;

import java.util.Optional;

@Component
public class TransactionMapper {

    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getFee(),
                transaction.getStatus(),
                extractId(transaction.getSourceWallet()),
                extractId(transaction.getTargetWallet()),
                transaction.getCreatedAt()
        );
    }

    private Long extractId(Wallet wallet) {
        return Optional.ofNullable(wallet).map(Wallet::getId).orElse(null);
    }
}
