# Services

One independently deployable Quarkus microservice per bounded context. Every service
follows the same hexagonal layout, exposes `/openapi`, `/q/health` and `/q/metrics`,
returns RFC 9457 problem details, and is configured only through environment variables
(Twelve-Factor). Lifecycle transitions take no body; `@Consumes` appears only on
endpoints that read one.

**Implementation order** (see `../docs/implementation-roadmap.md`):
Citizen Cases → Fees → Eligibility Rules → Open APIs.

| Service | Port | Alignment |
|---|---|---|
| `case-service` | 8081 | Public case management; privacy by design |
| `fee-service` | 8082 | Public fee integrity |
| `eligibility-rules-service` | 8083 | Program rules as auditable decisions |
| `agency-catalog-service` | 8084 | Government master data |
| `notification-service` | 8085 | Platform support (shared pattern) |

Run the pilot service:

```bash
mvn -pl services/case-service quarkus:dev
# OpenAPI UI: http://localhost:8081/docs — health: http://localhost:8081/q/health
```
