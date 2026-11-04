package org.openindustries.government.agency;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AgencyService {

    @Inject
    AgencyRepository repository;

    public Agency create(CreateAgencyRequest request) {
        return repository.save(Agency.create(request.name(), request.level(), request.servicesCount()));
    }

    public Agency get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Agency not found: " + id));
    }

    public List<Agency> list() {
        return repository.findAll();
    }

    public Agency suspend(UUID id) {
        Agency entity = get(id);
        if (!"ACTIVE".equals(entity.status)) {
            throw new DomainException(409, "Only a ACTIVE agency can transition via suspend");
        }
        entity.status = "SUSPENDED";
        return repository.save(entity);
    }
}
