package org.openindustries.government.functions.caserisk;

import io.quarkus.funqy.Funq;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Scores case risk signals (delay, document gaps, complaints) with an auditable rationale.
 * Transparent, auditable rules: NORMAL (< 35), WARNING (35-69), CRITICAL (>= 70);
 * the rationale is returned with the score for audit (ISO/IEC 27002 A.8.15).
 * Stateless by design (12-Factor VI): all state arrives in the request.
 */
public class Function {

    public record Event(String caseId, int delayIndex, int documentGapIndex, int complaintIndex) {
    }

    public record Score(String caseId, int score, String decision, String rationale) {
    }

    @Funq
    public Score caseRiskScorer(Event event) {
        int score = 0;
        StringBuilder why = new StringBuilder();
        if (event.delayIndex() >= 70) {
            score += 40;
            why.append("delayIndex >= 70; ");
        }
        if (event.documentGapIndex() >= 60) {
            score += 30;
            why.append("documentGapIndex >= 60; ");
        }
        if (event.complaintIndex() >= 50) {
            score += 30;
            why.append("complaintIndex >= 50; ");
        }
        String decision = score >= 70 ? "CRITICAL" : score >= 35 ? "WARNING" : "NORMAL";
        return new Score(event.caseId(), score, decision,
                why.length() == 0 ? "no signals" : why.toString().trim());
    }
}
