#!/bin/sh

podman run -d --name postgresql-expense -p 5432:5432 \
    -e POSTGRESQL_PASSWORD=expenses_pwd \
    -e POSTGRESQL_USER=expenses_user \
    -e POSTGRESQL_ADMIN_PASSWORD=admin_password \
    -e POSTGRESQL_DATABASE=expenses registry.access.redhat.com/rhscl/postgresql-10-rhel7:1
podman run -d --name prometheus-server -p 9090:9090 --net host \
    -v ./prometheus.yml:/etc/prometheus/prometheus.yml:Z quay.io/prometheus/prometheus:v2.22.2
podman run -d -p 3000:3000 --net host --name grafana-server \
    -v ./datasource.yml:/etc/grafana/provisioning/datasources/datasource.yml:Z \
    -v ./dashboard_config.yml:/etc/grafana/provisioning/dashboards/dashboard_config.yml:Z \
    -v ./demo_expenses_dashboard.json:/etc/dashboards/expense/demo_expense_dashboard.json:Z  quay.io/bitnami/grafana:7.3.4

echo "A database server for the expense-service app has been initiated."
echo "Prometheus server and Grafana server have been initiated."
echo "Prometheus has been configured to read metrics from the endpoint http://localhost:8080/metrics/application."
echo "Grafana has been configured to use Prometheus as its datasource to populate the dashboard 'Demo Expenses'."
