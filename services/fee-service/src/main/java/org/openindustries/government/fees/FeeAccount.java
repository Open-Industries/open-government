package org.openindustries.government.fees;

import java.util.UUID;

/**
 * FeeAccount aggregate — the guarded resource account of this core: a balance/capacity that
 * consumption operations draw from. Core invariant (ADR-0004): it NEVER goes negative;
 * an operation that would overdraw it is rejected and records nothing.
 */
public class FeeAccount {
    public UUID id;
        public java.util.UUID caseId;
    public long assessedFeeMinor;
    public String status;

    public static FeeAccount open(java.util.UUID caseId, long assessedFeeMinor) {
        FeeAccount a = new FeeAccount();
        a.id = UUID.randomUUID();
        a.caseId = caseId;
        a.assessedFeeMinor = assessedFeeMinor;
        a.status = "ACTIVE";
        return a;
    }
}
