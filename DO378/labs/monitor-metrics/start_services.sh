#!/bin/sh

podman run -d --name prometheus-server -p 9090:9090 --net host \
    -v ~/DO378/labs/monitor-metrics/prometheus.yml:/etc/prometheus/prometheus.yml:Z quay.io/prometheus/prometheus:v2.22.2
echo "Prometheus server and Grafana server have been initiated."
echo "Prometheus has been configured to read metrics from the endpoint http://localhost:8080/metrics/application."
podman run -d -p 3000:3000 --net host --name grafana-server \
    -v ~/DO378/labs/monitor-metrics/datasource.yml:/etc/grafana/provisioning/datasources/datasource.yml:Z \
    -v ~/DO378/labs/monitor-metrics/dashboard_config.yml:/etc/grafana/provisioning/dashboards/dashboard_config.yml:Z \
    -v ~/DO378/labs/monitor-metrics/demo_expenses_dashboard.json:/etc/dashboards/expense/demo_expense_dashboard.json:Z \
    quay.io/bitnami/grafana:7.3.4
echo "Grafana has been configured to use Prometheus as its datasource to populate the dashboard 'Demo Expenses'."
