package sn.exam.badwallet.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import sn.exam.shared.enums.DepositMethod;

import java.math.BigDecimal;

public record DepositRequest(
        @NotNull @DecimalMin("1.00") BigDecimal amount,
        @NotNull DepositMethod paymentMethod
) {}
