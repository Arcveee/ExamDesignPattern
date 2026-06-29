package sn.exam.badwallet.factory;

import org.springframework.stereotype.Component;
import sn.exam.badwallet.entity.Transaction;
import sn.exam.badwallet.entity.Wallet;
import sn.exam.shared.enums.TransactionStatus;
import sn.exam.shared.enums.TransactionType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class TransactionFactory {

    private static final List<TransactionType> TYPES = List.of(
            TransactionType.DEPOSIT,
            TransactionType.WITHDRAWAL,
            TransactionType.TRANSFER
    );
    private static final List<TransactionStatus> STATUSES = List.of(
            TransactionStatus.SUCCESS,
            TransactionStatus.SUCCESS,
            TransactionStatus.FAILED
    );
    private static final Random RANDOM = new Random();

    public Transaction createRandom(Wallet source, List<Wallet> allWallets) {
        TransactionType type = TYPES.get(RANDOM.nextInt(TYPES.size()));
        BigDecimal amount = randomAmount();
        Wallet target = resolveTarget(type, source, allWallets);

        return Transaction.builder()
                .type(type)
                .amount(amount)
                .fee(randomFee(amount))
                .status(STATUSES.get(RANDOM.nextInt(STATUSES.size())))
                .sourceWallet(source)
                .targetWallet(target)
                .createdAt(LocalDateTime.now().minusDays(RANDOM.nextInt(90)))
                .build();
    }

    private Wallet resolveTarget(TransactionType type, Wallet source, List<Wallet> allWallets) {
        if (type != TransactionType.TRANSFER || allWallets.size() < 2) return null;
        Wallet target;
        do {
            target = allWallets.get(RANDOM.nextInt(allWallets.size()));
        } while (target.getId().equals(source.getId()));
        return target;
    }

    private BigDecimal randomAmount() {
        return BigDecimal.valueOf(500 + RANDOM.nextInt(49_500))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal randomFee(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(0.01))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
