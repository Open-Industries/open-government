package org.openindustries.government.agency;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateAgencyRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "FEDERAL|STATE|MUNICIPAL") String level,
        @Positive int servicesCount) {
}
