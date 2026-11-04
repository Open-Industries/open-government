package org.openindustries.government.fees;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OpenFeeAccountRequest(
        @NotNull java.util.UUID caseId,
        @PositiveOrZero long assessedFeeMinor) {
}
