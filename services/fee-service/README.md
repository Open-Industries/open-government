# Fee Service

Bounded context: **Fees** — Assessed-fee accounts per case and the payments applied to them.

Alignment: Public fee integrity. Core invariant, enforced in code and tests: **the guarded
balance/capacity never goes negative** — over-drawing operations are rejected with `422`
and nothing is recorded. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8082` (`HTTP_PORT`) |
| Base path | `/api/v1/fees` |
| Owns data | FeeAccount accounts, FeePayment records |
| Publishes | `fees.consumed`, `fees.insufficient` |

Run locally: `mvn -pl services/fee-service quarkus:dev`.
