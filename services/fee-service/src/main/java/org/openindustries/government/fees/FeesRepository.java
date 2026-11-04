package org.openindustries.government.fees;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class FeesRepository {
    private final ConcurrentHashMap<UUID, FeeAccount> accounts = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<FeePayment> records = new CopyOnWriteArrayList<>();

    public FeeAccount saveAccount(FeeAccount account) {
        accounts.put(account.id, account);
        return account;
    }

    public Optional<FeeAccount> findAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public FeePayment saveRecord(FeePayment record) {
        records.add(record);
        return record;
    }

    public List<FeePayment> recordsOf(UUID accountId) {
        return records.stream().filter(r -> r.accountId.equals(accountId)).toList();
    }
}
