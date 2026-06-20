#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
LOCAL_HOME="${HOME}/.local"

MYSQL_HOME="${MYSQL_HOME:-${LOCAL_HOME}/tools/mysql-8.4.9-macos15-arm64}"
MYSQL_PORT="${MYSQL_PORT:-3306}"
MYSQL_USER="${MYSQL_USER:-root}"
MYSQL_PASSWORD="${MYSQL_PASSWORD:-}"
BACKEND_PORT="${BACKEND_PORT:-9000}"
MYSQL="${MYSQL_HOME}/bin/mysql"
MYSQLADMIN="${MYSQL_HOME}/bin/mysqladmin"
MYSQL_STARTED_MARKER="${ROOT_DIR}/scripts/.mysql-started-by-start-all"

log() {
  printf '[stop-all] %s\n' "$1"
}

port_pid() {
  lsof -tiTCP:"$1" -sTCP:LISTEN 2>/dev/null | head -n 1 || true
}

stop_pid_file() {
  local pid_file="$1"
  local name="$2"

  if [[ ! -f "${pid_file}" ]]; then
    return
  fi

  local pid
  pid="$(cat "${pid_file}" 2>/dev/null || true)"
  if [[ -n "${pid}" ]] && kill -0 "${pid}" 2>/dev/null; then
    log "Stopping ${name} pid ${pid}"
    kill "${pid}" 2>/dev/null || true
    sleep 2
    if kill -0 "${pid}" 2>/dev/null; then
      kill -9 "${pid}" 2>/dev/null || true
    fi
  fi
  rm -f "${pid_file}"
}

stop_port() {
  local port="$1"
  local name="$2"
  local pid
  pid="$(port_pid "${port}")"
  if [[ -n "${pid}" ]]; then
    log "Stopping ${name} on port ${port} pid ${pid}"
    kill "${pid}" 2>/dev/null || true
    sleep 2
    if kill -0 "${pid}" 2>/dev/null; then
      kill -9 "${pid}" 2>/dev/null || true
    fi
  fi
}

mysqladmin_command() {
  if [[ -n "${MYSQL_PASSWORD}" ]]; then
    "${MYSQLADMIN}" -h127.0.0.1 -P"${MYSQL_PORT}" -u"${MYSQL_USER}" "-p${MYSQL_PASSWORD}" "$@"
  else
    "${MYSQLADMIN}" -h127.0.0.1 -P"${MYSQL_PORT}" -u"${MYSQL_USER}" "$@"
  fi
}

stop_mysql_if_started_here() {
  if [[ "${STOP_MYSQL_ALWAYS:-0}" != "1" && ! -f "${MYSQL_STARTED_MARKER}" ]]; then
    log "Skipping MySQL stop because it was already running before start-all.sh."
    log "Use STOP_MYSQL_ALWAYS=1 ./scripts/stop-all.sh if you really want to stop MySQL."
    return
  fi

  if [[ ! -x "${MYSQLADMIN}" ]]; then
    log "mysqladmin not found: ${MYSQLADMIN}"
    return
  fi

  log "Stopping MySQL on port ${MYSQL_PORT}"
  mysqladmin_command shutdown 2>/dev/null || stop_port "${MYSQL_PORT}" "MySQL"
  rm -f "${MYSQL_STARTED_MARKER}"
}

main() {
  stop_pid_file "${ROOT_DIR}/client-vue/.vite.pid" "frontend"
  if [[ -f "${ROOT_DIR}/client-vue/.vite.port" ]]; then
    stop_port "$(cat "${ROOT_DIR}/client-vue/.vite.port")" "frontend"
    rm -f "${ROOT_DIR}/client-vue/.vite.port"
  fi

  stop_pid_file "${ROOT_DIR}/backend-springboot/.springboot.pid" "backend"
  stop_port "${BACKEND_PORT}" "backend"

  stop_mysql_if_started_here
  log "Stopped project services."
}

main "$@"
