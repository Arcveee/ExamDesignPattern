package sn.exam.badwallet.dto;

import java.math.BigDecimal;

public record BalanceResponse(
        BigDecimal balance,
        String currency
) {}
