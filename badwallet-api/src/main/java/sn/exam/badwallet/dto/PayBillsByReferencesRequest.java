package sn.exam.badwallet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PayBillsByReferencesRequest(
        @NotBlank String phoneNumber,
        @NotBlank String serviceName,
        @NotEmpty List<String> factureReferences
) {}
