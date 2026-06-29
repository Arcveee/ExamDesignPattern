package sn.exam.badwallet.dto;

import jakarta.validation.constraints.NotBlank;

public record PayCurrentBillRequest(
        @NotBlank String phoneNumber,
        @NotBlank String serviceName
) {}
