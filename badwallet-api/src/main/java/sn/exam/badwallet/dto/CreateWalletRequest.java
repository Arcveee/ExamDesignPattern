package sn.exam.badwallet.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateWalletRequest(
        @NotBlank @Pattern(regexp = "^\\+221[0-9]{9}$") String phoneNumber,
        @NotBlank @Email String email,
        @NotNull @DecimalMin("0.00") BigDecimal initialBalance,
        @NotBlank @Size(min = 6, max = 20) String code,
        @NotBlank @Size(min = 3, max = 3) String currency
) {}
