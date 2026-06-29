package sn.exam.shared.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BillResponse(
        String billReference,
        String provider,
        String subscriberName,
        BigDecimal amount,
        boolean paid,
        LocalDate billDate
) {}
