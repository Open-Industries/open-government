package org.openindustries.government.eligibility;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record EvaluateRequest(
        @NotBlank @Pattern(regexp = "HOUSING|HEALTH|EDUCATION|BUSINESS") String programType,
        @Positive int requiredMaxProcessingDays,
        @Positive long requiredMaxBenefitMinor) {
}
