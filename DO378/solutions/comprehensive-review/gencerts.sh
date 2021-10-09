#!/bin/bash

##########  Utility function ################################

exit_if_error() {
  local exit_code=$1
  [[ $exit_code ]] &&                                    # do nothing if no error code passed
    ((exit_code != 0)) && {                              # do nothing if error code is 0
      exit "$exit_code"                                  # we could also check to make sure error code is numeric when passed
    }
}

##########  Utility function ################################

SERVICE="currency"
KEYSTORE_PASS="redhat"
KEY_PASS="redhat"
TRUSTSTORE_PASS="redhat"

# echo "Generating keystore file for ${SERVICE}..."
keytool -noprompt -genkeypair -keyalg RSA -keysize 2048 -validity 365 \
  -dname "CN=${SERVICE}, OU=Training, O=RedHat, L=Raleigh, ST=NC, C=US" \
  -ext "SAN:c=DNS:localhost,IP:127.0.0.1" \
  -alias ${SERVICE} -storetype JKS \
  -storepass ${KEYSTORE_PASS} -keypass ${KEY_PASS} \
  -keystore ${SERVICE}.keystore \
  || exit_if_error $? 

# echo "Exporting certificate for ${SERVICE}..." 
keytool -exportcert -keystore ${SERVICE}.keystore \
  -storepass ${KEYSTORE_PASS} -alias ${SERVICE} \
  -rfc -file ${SERVICE}.crt \
  || exit_if_error $? 
# echo "Importing certificate for ${SERVICE} into trust store..."

keytool -noprompt -keystore ${SERVICE}.truststore \
  -importcert -file ${SERVICE}.crt \
  -alias ${SERVICE} -storepass ${TRUSTSTORE_PASS} \
  || exit_if_error $? 

exit 0
