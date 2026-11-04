package org.openindustries.government.fees;

import java.time.Instant;
import java.util.UUID;

/** One guarded consumption record — the auditable evidence of a draw on a FeeAccount. */
public class FeePayment {
    public UUID id;
    public UUID accountId;
    public String channel;
    public long amountMinor;
    public long remainingAfter;
    public Instant recordedAt;

    public static FeePayment of(UUID accountId, String channel, long amountMinor, long remainingAfter) {
        FeePayment r = new FeePayment();
        r.id = UUID.randomUUID();
        r.accountId = accountId;
        r.channel = channel;
        r.amountMinor = amountMinor;
        r.remainingAfter = remainingAfter;
        r.recordedAt = Instant.now();
        return r;
    }
}
