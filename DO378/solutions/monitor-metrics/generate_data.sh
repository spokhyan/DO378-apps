#!/bin/sh


echo "Sending requests to GET http://localhost:8080/expenses/ and POST http://localhost:8080/expenses/"

for i in {1..1800}
do
  curl http://localhost:8080/expenses/ &>/dev/null
  name=$(openssl rand -hex 16)
  curl -XPOST -H "Content-type: application/json" -d "{\"name\":\"${name}\", \"paymentMethod\":\"CASH\", \"amount\":\"${RANDOM}\"}" http://localhost:8080/expenses/ &>/dev/null
done
