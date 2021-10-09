#!/bin/sh

echo "*************************************"
echo "Starting the 'frontend' service      "
echo "Press Ctrl+C to terminate            "
echo "*************************************"
cd ~/DO378/labs/comprehensive-review/frontend && \
npm install && \
(REACT_APP_GW_ENDPOINT="http://localhost:8080" REACT_APP_NEWS_ENABLED=true npm start)