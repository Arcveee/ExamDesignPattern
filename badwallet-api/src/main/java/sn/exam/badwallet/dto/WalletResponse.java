package sn.exam.badwallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WalletResponse(
        Long id,
        String phoneNumber,
        String ownerName,
        BigDecimal balance,
        String currency,
        LocalDateTime createdAt
) {}
