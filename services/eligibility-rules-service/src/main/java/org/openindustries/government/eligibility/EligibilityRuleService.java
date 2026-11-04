package org.openindustries.government.eligibility;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EligibilityRuleService {

    @Inject
    EligibilityRuleRepository repository;

    public EligibilityRule create(CreateEligibilityRuleRequest request) {
        return repository.save(EligibilityRule.create(request.name(), request.programType(),
                request.maxBenefitMinor(), request.maxProcessingDays(), request.priority()));
    }

    public EligibilityRule get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "EligibilityRule not found: " + id));
    }

    public List<EligibilityRule> list() {
        return repository.findAll();
    }

    public EligibilityRule activate(UUID id) {
        EligibilityRule rule = get(id);
        rule.status = "ACTIVE";
        return repository.save(rule);
    }

    public Evaluation evaluate(EvaluateRequest request) {
        var candidates = repository.activeFor(request.programType());
        if (candidates.isEmpty()) {
            return new Evaluation("DENIED", null,
                    "No active rule for " + request.programType());
        }
        for (EligibilityRule rule : candidates) {
            if (rule.maxProcessingDays <= request.requiredMaxProcessingDays()
                    && rule.maxBenefitMinor >= request.requiredMaxBenefitMinor()) {
                return new Evaluation("ALLOWED", rule.id,
                        "Rule '" + rule.name + "' covers the request");
            }
        }
        return new Evaluation("DEGRADED", candidates.get(0).id,
                "Active rules exist for " + request.programType() + " but none meets the requirement");
    }
}
