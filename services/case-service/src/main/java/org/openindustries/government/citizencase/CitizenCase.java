package org.openindustries.government.citizencase;

import java.time.Instant;
import java.util.UUID;

/**
 * CitizenCase aggregate — Citizen case registry; the reference code (folio) is masked at intake.
 *
 * Privacy by design (ISO/IEC 27002 A.5.34): secret identifiers are validated at
 * creation and NEVER persisted or returned in clear — only a masked form is kept.
 * All identifiers in this reference implementation are synthetic.
 */
public class CitizenCase {
    public UUID id;
        public String caseType;
        public String referenceCodeMasked;
        public String agencyCode;
    public String status;
    public Instant createdAt;

    public static CitizenCase create(String caseType, String referenceCode, String agencyCode) {
        CitizenCase e = new CitizenCase();
        e.id = UUID.randomUUID();
        e.caseType = caseType;
        e.referenceCodeMasked = mask(referenceCode);
        e.agencyCode = agencyCode;
        e.status = "OPEN";
        e.createdAt = Instant.now();
        return e;
    }

    static String mask(String value) {
        if (value == null || value.length() < 4) {
            return "****";
        }
        return "*".repeat(value.length() - 4) + value.substring(value.length() - 4);
    }
}
