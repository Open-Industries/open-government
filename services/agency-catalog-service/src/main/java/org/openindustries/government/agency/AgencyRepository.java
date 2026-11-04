package org.openindustries.government.agency;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class AgencyRepository {
    private final ConcurrentHashMap<UUID, Agency> store = new ConcurrentHashMap<>();

    public Agency save(Agency entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<Agency> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Agency> findAll() {
        return List.copyOf(store.values());
    }
}
