package org.openindustries.government.functions.transparency;

import io.quarkus.funqy.Funq;

import java.util.ArrayList;
import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Compares overdue cases and open-data coverage against agency targets; one finding per breach.
 * Compares measured values with targets and returns a verdict with one finding per
 * breach — machine-checkable assurance evidence.
 */
public class Function {

    public record Kpi(String agencyId, int measuredOverdueCases, int targetOverdueCases,
                      double measuredOpenDataPct, double targetOpenDataPct) {
    }

    public record Verdict(String agencyId, boolean compliant, List<String> findings) {
    }

    @Funq
    public Verdict transparencyEvaluator(Kpi kpi) {
        List<String> findings = new ArrayList<>();
        if (kpi.measuredOverdueCases() > kpi.targetOverdueCases()) {
            findings.add("overdueCases " + kpi.measuredOverdueCases() + " exceeds target " + kpi.targetOverdueCases());
        }
        if (kpi.measuredOpenDataPct() < kpi.targetOpenDataPct()) {
            findings.add("openDataPct " + kpi.measuredOpenDataPct() + " below target " + kpi.targetOpenDataPct());
        }
        return new Verdict(kpi.agencyId(), findings.isEmpty(), findings);
    }
}
