package sn.exam.badwallet.dto;

import sn.exam.shared.enums.TransactionStatus;
import sn.exam.shared.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        TransactionType type,
        BigDecimal amount,
        BigDecimal fee,
        TransactionStatus status,
        Long sourceWalletId,
        Long targetWalletId,
        LocalDateTime createdAt
) {}
