#!/bin/sh

echo "*************************************"
echo "Starting the 'news' service"
echo "Press Ctrl+C to terminate            "
echo "*************************************"
cd ~/DO378/labs/comprehensive-review/news/src && \
pip3 install -r ../requirements.txt --user && \
python3 gossip.py
