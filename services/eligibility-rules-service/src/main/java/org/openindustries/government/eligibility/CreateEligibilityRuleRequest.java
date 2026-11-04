package org.openindustries.government.eligibility;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateEligibilityRuleRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "HOUSING|HEALTH|EDUCATION|BUSINESS") String programType,
        @Positive long maxBenefitMinor,
        @Positive int maxProcessingDays,
        @Min(1) @Max(100) int priority) {
}
