package org.openindustries.government.fees;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FeesService {

    @Inject
    FeesRepository repository;

    public FeeAccount open(OpenFeeAccountRequest request) {
        return repository.saveAccount(FeeAccount.open(request.caseId(), request.assessedFeeMinor()));
    }

    public FeeAccount get(UUID id) {
        return repository.findAccount(id)
                .orElseThrow(() -> new DomainException(404, "FeeAccount not found: " + id));
    }

    /**
     * Draws from the account. Invariant: the balance/capacity never goes negative —
     * an over-drawing operation is rejected (422) and nothing is recorded.
     */
    public FeePayment consume(UUID accountId, CreateFeePaymentRequest request) {
        FeeAccount account = get(accountId);
        if (!"ACTIVE".equals(account.status)) {
            throw new DomainException(409, "FeeAccount is not ACTIVE");
        }
        if (request.amountMinor() > account.assessedFeeMinor) {
            throw new DomainException(422, "Insufficient capacity: requested " + request.amountMinor()
                    + " exceeds available " + account.assessedFeeMinor);
        }
        account.assessedFeeMinor -= request.amountMinor();
        repository.saveAccount(account);
        return repository.saveRecord(FeePayment.of(accountId, request.channel(), request.amountMinor(), account.assessedFeeMinor));
    }

    public List<FeePayment> recordsOf(UUID accountId) {
        get(accountId);
        return repository.recordsOf(accountId);
    }
}
