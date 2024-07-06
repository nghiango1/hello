#!/bin/bash

# Function to start Ngrok server
start_ngrok() {
    ngrok tcp 192.168.1.5:1194 &
    NGROK_PID=$!
    echo "Ngrok server started with PID $NGROK_PID"
}

# Function to stop Ngrok server
stop_ngrok() {
    if [[ -n "$NGROK_PID" ]]; then
        echo "Stopping Ngrok server with PID $NGROK_PID"
        kill $NGROK_PID
        unset NGROK_PID
    fi
}

# Start Ngrok server if PC is connected to the internet
if ping -q -c 1 -W 1 google.com >/dev/null; then
    start_ngrok
else
    echo "PC is not connected to the internet."
fi

# Monitor internet connection and restart Ngrok server if PC is reconnected
while true; do
    if ping -q -c 1 -W 1 google.com >/dev/null; then
        if [[ -z "$NGROK_PID" ]]; then
            start_ngrok
        fi
    else
        stop_ngrok
    fi
    sleep 5
done

