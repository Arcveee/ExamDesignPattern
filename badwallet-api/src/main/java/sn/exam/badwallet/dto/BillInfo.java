package sn.exam.badwallet.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BillInfo(
        String reference,
        String provider,
        String subscriberName,
        BigDecimal amount,
        boolean paid,
        LocalDate billDate
) {}
