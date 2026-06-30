package sn.exam.badwallet.factory;

import org.springframework.stereotype.Component;
import sn.exam.badwallet.entity.Wallet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class WalletFactory {

    private static final List<String> PREFIXES = List.of("70", "75", "76", "77", "78");
    private static final List<String> FIRST_NAMES = List.of("Amadou", "Fatou", "Mamadou", "Awa", "Ousmane", "Aminata", "Cheikh", "Mariama", "Ibrahima", "Ndeye", "Modou", "Khady", "Abdoulaye", "Aïssatou", "Moussa");
    private static final Random RANDOM = new Random();

    public Wallet createRandom() {
        return Wallet.builder()
                .phoneNumber(randomPhone())
                .ownerName(randomSenegaleseName())
                .code(randomCode())
                .balance(randomBalance())
                .currency("XOF")
                .build();
    }

    private String randomSenegaleseName() {
        return FIRST_NAMES.get(RANDOM.nextInt(FIRST_NAMES.size()));
    }

    private String randomPhone() {
        String prefix = PREFIXES.get(RANDOM.nextInt(PREFIXES.size()));
        String suffix = String.format("%07d", RANDOM.nextInt(10_000_000));
        return "+221" + prefix + suffix;
    }



    private String randomCode() {
        return "WLT-" + UUID.randomUUID().toString().replace("-", "").substring(0, 7).toUpperCase();
    }

    private BigDecimal randomBalance() {
        return BigDecimal.valueOf(1000 + RANDOM.nextInt(999_000))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
