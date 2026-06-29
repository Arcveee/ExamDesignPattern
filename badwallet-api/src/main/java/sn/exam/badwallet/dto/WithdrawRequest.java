package sn.exam.badwallet.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WithdrawRequest(
        @NotBlank String phoneNumber,
        @NotNull @DecimalMin("1.00") BigDecimal amount
) {}
