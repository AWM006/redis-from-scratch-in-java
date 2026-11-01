#!/usr/bin/env bash
cd "$(dirname "$0")" || exit 1

LOG="app.log"
PORT=8080
: > "$LOG"

IS_WIN=0
case "$(uname -s)" in MINGW*|MSYS*|CYGWIN*) IS_WIN=1 ;; esac

# Process groups only on Linux/macOS
[ "$IS_WIN" = 0 ] && set -m

./mvnw spring-boot:run < /dev/null > "$LOG" 2>&1 &
MVN_PID=$!
SCRIPT_PID=$$

kill_server() {
    if [ "$IS_WIN" = 1 ]; then
        for pid in $(netstat -ano | grep ":$PORT " | grep LISTENING | awk '{print $5}'); do
            taskkill //F //T //PID "$pid" >/dev/null 2>&1
        done
        kill -9 "$MVN_PID" 2>/dev/null
    else
        kill -TERM -- "-$MVN_PID" 2>/dev/null || kill -TERM "$MVN_PID" 2>/dev/null
        for _ in 1 2 3 4 5; do
            kill -0 "$MVN_PID" 2>/dev/null || break
            sleep 1
        done
        kill -KILL -- "-$MVN_PID" 2>/dev/null || kill -KILL "$MVN_PID" 2>/dev/null
        command -v lsof >/dev/null 2>&1 &&
            lsof -ti tcp:"$PORT" -sTCP:LISTEN 2>/dev/null | xargs kill -9 2>/dev/null
    fi
}

# Watchdog: stops the server once this script is gone
(
    trap '' HUP INT TERM
    while kill -0 "$SCRIPT_PID" 2>/dev/null; do sleep 1; done
    kill_server
) >/dev/null 2>&1 &
WATCHDOG_PID=$!
disown "$WATCHDOG_PID" 2>/dev/null

stop_server() {
    trap - EXIT HUP INT TERM
    kill "$WATCHDOG_PID" 2>/dev/null
    kill_server
}

trap 'stop_server; echo "Server stopped."; exit 0' EXIT HUP INT TERM

while true; do
    sleep 1
    if grep -q "BUILD FAILURE" "$LOG" 2>/dev/null; then
        echo "Spring failed to start. Check $LOG"
        exit 1
    fi
    if ! kill -0 "$MVN_PID" 2>/dev/null; then
        echo "Spring exited unexpectedly. Check $LOG"
        exit 1
    fi
    if grep -q "Started RedisApplication" "$LOG" 2>/dev/null; then
        break
    fi
done

echo "Spring running"

while true; do
    printf '> '
    IFS= read -r line || { echo; echo "Input closed, stopping."; break; }
    cmd=$(printf '%s' "$line" | tr '[:upper:]' '[:lower:]' | tr -d '[:space:]')
    [ "$cmd" = "/stop" ] && break
done

exit 0