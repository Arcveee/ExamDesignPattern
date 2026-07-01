package sn.exam.shared.dto;

import java.math.BigDecimal;

public record BillResponse(
        String billReference,
        String provider,
        String subscriberName,
        BigDecimal amount,
        boolean paid
) {}
