#!/bin/bash

if [ $# -lt 3 ]; then
  echo 1>&2 "Usage: . $0 hostname realm clientid [username [password [client_password]]]"
  echo 1>&2 "  options:"
  echo 1>&2 "    hostname: localhost:8081"
  echo 1>&2 "    realm:    quarkus"
  echo 1>&2 "    clientid: backend-service"
  echo 1>&2 "    username: jdoe/alice"
  echo 1>&2 "    password: jdoe/alice"
  echo 1>&2 "    client_password: secret"
  exit 
fi

HOSTNAME=$1
REALM_NAME=$2
CLIENT_ID=$3
USERNAME=$4
PASSWORD=$5
CLIENT_SECRET=$6

KEYCLOAK_URL=http://$HOSTNAME/auth/realms/$REALM_NAME/protocol/openid-connect/token

echo 1>&2 "Using Keycloak: $KEYCLOAK_URL"
echo 1>&2 "realm: $REALM_NAME"
echo 1>&2 "client-id: $CLIENT_ID"

if [ -z "$CLIENT_SECRET" ] ; then
    echo 1>&2 -n Secret for client ${CLIENT_ID}: 
    read -s CLIENT_SECRET
    echo 1>&2
fi

if [ -z "$USERNAME" ] ; then
    echo 1>&2 -n Username: 
    read USERNAME
else
    echo 1>&2 "username: $USERNAME" 
fi

if [ -z "$PASSWORD" ] ; then
    echo 1>&2 -n Password for ${USERNAME}: 
    read -s PASSWORD
    echo 1>&2
fi

export TOKEN=$(curl -s -X POST "$KEYCLOAK_URL" \
 -H "Content-Type: application/x-www-form-urlencoded" \
 -d "username=$USERNAME" \
 -d "password=$PASSWORD" \
 -d 'grant_type=password' \
 -d "client_id=$CLIENT_ID" \
 -d "client_secret=$CLIENT_SECRET" \
 | jq -r '.access_token')

echo $TOKEN
if [ "$TOKEN" == "null" ]; then
    echo 1>&2 "Token was not retrieved! Review input parameters."
    exit 1
else
    echo 1>&2 "Token succesfuly retrieved."
    exit 0
fi