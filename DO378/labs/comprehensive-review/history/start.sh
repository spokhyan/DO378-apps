#!/bin/sh

echo "*************************************"
echo "Starting the 'history' service "
echo "Press Ctrl+C to terminate            "
echo "*************************************"
cd ~/DO378/labs/comprehensive-review/history && \
npm install && \
(PORT=8082 node index.js)