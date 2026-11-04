# Agency Catalog Service

Bounded context: **Agencies** — Catalogue of public agencies and their service counts.

Alignment: Government master data. Lifecycle transitions take no body. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8084` (`HTTP_PORT`) |
| Base path | `/api/v1/agencies` |
| Owns data | Agency records |

Run locally: `mvn -pl services/agency-catalog-service quarkus:dev`.
