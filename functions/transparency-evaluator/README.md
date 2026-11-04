# Transparency Evaluator (serverless function)

Quarkus Funqy function on Knative (scale to zero). Compares overdue cases and open-data coverage against agency targets; one finding per breach.

Invoke: `POST /transparencyEvaluator` with the KPI payload.
