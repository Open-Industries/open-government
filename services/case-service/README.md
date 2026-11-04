# Case Service

Bounded context: **Citizen Cases** — Citizen case registry; the reference code (folio) is masked at intake.

Alignment: Public case management; privacy by design. Lifecycle transitions take no body; secret identifiers,
where the domain has them, are masked at creation and never stored or returned in clear.
All data is synthetic.

| Item | Value |
|---|---|
| Port | `8081` (`HTTP_PORT`) |
| Base path | `/api/v1/cases` |
| Owns data | CitizenCase records |
| Publishes | `citizencase.created`, lifecycle events (see `contracts/asyncapi.yaml`) |

Run locally: `mvn -pl services/case-service quarkus:dev`.
