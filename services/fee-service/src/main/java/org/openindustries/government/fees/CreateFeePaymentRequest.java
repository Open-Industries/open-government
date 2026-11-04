package org.openindustries.government.fees;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateFeePaymentRequest(
        @NotBlank @Pattern(regexp = "ONLINE|BANK|OFFICE") String channel,
        @Positive long amountMinor) {
}
