# Open Government — Reference Digital Government Core Platform

A **standard, generic digital government and public services core platform** that any organization in this vertical
can deploy and adapt: built as **microservices and serverless functions** on
**Java 21 + Quarkus**, aligned with **The Twelve-Factor App**, documented under the ISO
architecture and quality standards, and aligned with the domain standards of the
vertical (see the traceability matrix). Everything is *aligned to* the cited standards —
**no certification, conformance or regulatory-compliance claim is made**. All data is
**synthetic**.

> Reference implementation: small, complete and runnable. The rules that matter in this
> domain are enforced in code and covered by tests — start with the invariant:
> payments applied to a public case never exceed its assessed fee — a payment that would overpay is rejected (422) and records nothing; public money movements are always fully evidenced.

## Standards alignment

| Standard / framework | What it governs here | Where |
|---|---|---|
| ISO/IEC/IEEE **42010** | Architecture description | `docs/architecture/architecture-description.md` |
| ISO/IEC **25010** (SQuaRE) | Product quality model and scenarios | `docs/architecture/quality-attributes.md` |
| ISO/IEC/IEEE **15288 & 12207** | Life-cycle processes | `docs/lifecycle/software-lifecycle.md` |
| ISO/IEC **27001 & 27002** | Information security | `docs/security/` |
| **The Twelve-Factor App** | Cloud-native methodology | `docs/architecture/twelve-factor.md` |
| W3C DCAT / DCAT-AP | Open data catalogues | Dataset/catalogue posture for transparency outputs |
| NIST SP 800-53 | Security and privacy controls | Control posture complementing the ISO 27002 mapping |
| WCAG 2.2 | Accessibility of public services | Accessible contract commitment |
| ISO 37120 | City service and quality-of-life indicators | Indicator posture for transparency evaluation |

Full traceability: `docs/compliance/standards-traceability.md`.

## The platform at a glance

### Microservices (Quarkus, `services/`)

| Service | Port | Alignment | Responsibility |
|---|---|---|---|
| `case-service` | 8081 | Public case management; privacy by design | Citizen case registry; the reference code (folio) is masked at intake. |
| `fee-service` | 8082 | Public fee integrity | Assessed-fee accounts per case and the payments applied to them. |
| `eligibility-rules-service` | 8083 | Program rules as auditable decisions | Decision rules binding program types to benefit caps and processing envelopes. |
| `agency-catalog-service` | 8084 | Government master data | Catalogue of public agencies and their service counts. |
| `notification-service` | 8085 | Platform support (shared pattern) | Channel-agnostic dispatch of domain notifications from events. |

### Serverless functions (Quarkus Funqy + Knative, `functions/`)

| Function | Trigger | Responsibility |
|---|---|---|
| `case-risk-scorer` | Event / HTTP | Scores case risk signals (delay, document gaps, complaints) with an auditable rationale. |
| `transparency-evaluator` | Event / HTTP | Compares overdue cases and open-data coverage against agency targets; one finding per breach. |

## Design principles

1. **One bounded context per service**, identical hexagonal layout everywhere.
2. **The domain invariant is code, not prose** — payments applied to a public case never exceed its assessed fee — a payment that would overpay is rejected (422) and records nothing; public money movements are always fully evidenced.
3. **Privacy by design** — Case reference codes are validated at intake and never stored or returned in clear — only a masked form is kept. Citizen personal data is minimized; all data here is synthetic.
4. **Database per service, API-first (OpenAPI), events as CloudEvents.**
5. **Adapt by configuration** — rules, thresholds, tariffs/rates and providers are data
   and configuration, not code forks (see *Adaptation guide*).

## Quick start

Prerequisites: Java 21, Maven 3.9+, optionally Docker/Podman and Knative/OpenShift.

```bash
mvn verify                                    # build and test everything
mvn -pl services/case-service quarkus:dev
docker compose up -d                          # local PostgreSQL, Kafka, Keycloak
```

## Deployment

- **Kubernetes / OpenShift**: `kubectl apply -k deploy/kubernetes/`.
- **Serverless**: Knative Services in `deploy/knative/` (scale to zero).
- **Local**: `docker-compose.yml` for dependencies; services run with `quarkus:dev`.

## Adaptation guide

| You want to… | Change only… |
|---|---|
| Adjust domain rules and thresholds | Rule data in the `eligibility-rules-service` context and function configuration |
| Change the guarded capacity semantics | The `fee-service` context (invariant stays: never negative) |
| Plug your IdP / providers | OIDC at the gateway; provider adapters at the boundary |
| Replace in-memory persistence | The repository port's JPA adapter on the service's own database (`DB_KIND`, `DB_URL`) |

## Documentation map

- Architecture (42010): `docs/architecture/` · Decisions: `docs/adr/`
- Domain standards: `docs/domain/domain-standards.md`
- Quality (25010), Security (27001/27002), Life cycle (15288/12207), API, Data, Operations: under `docs/`

## License

Apache-2.0 — see `LICENSE`.
