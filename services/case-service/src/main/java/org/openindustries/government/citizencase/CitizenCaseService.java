package org.openindustries.government.citizencase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CitizenCaseService {

    @Inject
    CitizenCaseRepository repository;

    public CitizenCase create(CreateCitizenCaseRequest request) {
        return repository.save(CitizenCase.create(request.caseType(), request.referenceCode(), request.agencyCode()));
    }

    public CitizenCase get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "CitizenCase not found: " + id));
    }

    public List<CitizenCase> list() {
        return repository.findAll();
    }

    public CitizenCase assign(UUID id) {
        CitizenCase entity = get(id);
        if ("RESOLVED".equals(entity.status)) {
            throw new DomainException(409, "A resolved citizencase is terminal");
        }
        if ("IN_PROGRESS".equals(entity.status)) {
            throw new DomainException(409, "CitizenCase already IN_PROGRESS");
        }
        entity.status = "IN_PROGRESS";
        return repository.save(entity);
    }

    public CitizenCase resolve(UUID id) {
        CitizenCase entity = get(id);
        if ("RESOLVED".equals(entity.status)) {
            throw new DomainException(409, "A resolved citizencase is terminal");
        }
        if ("RESOLVED".equals(entity.status)) {
            throw new DomainException(409, "CitizenCase already RESOLVED");
        }
        entity.status = "RESOLVED";
        return repository.save(entity);
    }
}
