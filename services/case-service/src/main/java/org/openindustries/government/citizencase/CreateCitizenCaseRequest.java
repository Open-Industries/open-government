package org.openindustries.government.citizencase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateCitizenCaseRequest(
        @NotBlank @Pattern(regexp = "PERMIT|LICENSE|BENEFIT|COMPLAINT") String caseType,
        @NotBlank String referenceCode,
        @NotBlank String agencyCode) {
}
