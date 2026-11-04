package org.openindustries.government.eligibility;

import java.util.UUID;

/**
 * EligibilityRule aggregate — a decision rule of this core: for one programType, it guarantees a
 * capacity (maxBenefitMinor) and a threshold (maxProcessingDays), with a precedence priority.
 * Only ACTIVE rules decide; every decision names its rule and its reason (audit).
 */
public class EligibilityRule {
    public UUID id;
    public String name;
    public String programType;
    public long maxBenefitMinor;
    public int maxProcessingDays;
    public int priority;
    public String status;

    public static EligibilityRule create(String name, String programType, long maxBenefitMinor, int maxProcessingDays, int priority) {
        EligibilityRule r = new EligibilityRule();
        r.id = UUID.randomUUID();
        r.name = name;
        r.programType = programType;
        r.maxBenefitMinor = maxBenefitMinor;
        r.maxProcessingDays = maxProcessingDays;
        r.priority = priority;
        r.status = "DRAFT";
        return r;
    }
}
