package sn.exam.badwallet.dto;

import jakarta.validation.constraints.*;

public record CreateWalletRequest(
        @NotBlank @Pattern(regexp = "^[0-9]{9,15}$") String phoneNumber,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, max = 6) String code,
        @NotBlank @Size(min = 3, max = 3) String currency
) {}
