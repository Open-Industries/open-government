# Eligibility Rules Service

Bounded context: **Eligibility Rules** — Decision rules binding program types to benefit caps and processing envelopes.

Alignment: Program rules as auditable decisions. Decisions are `ALLOWED` / `DEGRADED` / `DENIED`, always
with the matched rule and a human-readable reason for audit. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8083` (`HTTP_PORT`) |
| Base path | `/api/v1/eligibility-rules` |
| Owns data | EligibilityRule rules |
| Publishes | `eligibility.activated` |

Run locally: `mvn -pl services/eligibility-rules-service quarkus:dev`.
