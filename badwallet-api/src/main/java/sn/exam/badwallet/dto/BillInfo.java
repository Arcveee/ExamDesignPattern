package sn.exam.badwallet.dto;

import java.math.BigDecimal;

public record BillInfo(
        String reference,
        String provider,
        String subscriberName,
        BigDecimal amount,
        boolean paid
) {}
