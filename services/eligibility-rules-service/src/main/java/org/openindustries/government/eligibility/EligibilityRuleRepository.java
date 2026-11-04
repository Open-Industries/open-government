package org.openindustries.government.eligibility;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class EligibilityRuleRepository {
    private final ConcurrentHashMap<UUID, EligibilityRule> store = new ConcurrentHashMap<>();

    public EligibilityRule save(EligibilityRule rule) {
        store.put(rule.id, rule);
        return rule;
    }

    public Optional<EligibilityRule> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<EligibilityRule> findAll() {
        return List.copyOf(store.values());
    }

    /** Active rules for a scope, highest priority (lowest number) first. */
    public List<EligibilityRule> activeFor(String programType) {
        return store.values().stream()
                .filter(r -> r.programType.equals(programType) && "ACTIVE".equals(r.status))
                .sorted(Comparator.comparingInt(r -> r.priority))
                .toList();
    }
}
