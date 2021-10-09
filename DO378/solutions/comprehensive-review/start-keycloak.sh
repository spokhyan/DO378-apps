#!/bin/sh

echo "*************************************"
echo "Starting the 'KeyCloak' container    "
echo "Press Ctrl+C to terminate            "
echo "*************************************"
podman rm -f kc
podman run \
     -e KEYCLOAK_USER=USERNAME -e KEYCLOAK_PASSWORD=PASSWORD -e JAVA_OPTS_APPEND="-Dkeycloak.profile.feature.upload_script=enabled" \
     -p 8081:8080 \
     -v ~/DO378/labs/comprehensive-review/quarkus-realm.json:/tmp/quarkus-realm.json:z -e KEYCLOAK_IMPORT=/tmp/quarkus-realm.json \
     --name kc quay.io/keycloak/keycloak:10.0.1
