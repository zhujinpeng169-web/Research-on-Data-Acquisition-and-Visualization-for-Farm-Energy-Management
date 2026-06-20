#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
LOCAL_HOME="${HOME}/.local"

MYSQL_HOME="${MYSQL_HOME:-${LOCAL_HOME}/tools/mysql-8.4.9-macos15-arm64}"
MYSQL_DATA_DIR="${MYSQL_DATA_DIR:-${LOCAL_HOME}/mysql-data}"
MYSQL_SOCKET="${MYSQL_SOCKET:-${LOCAL_HOME}/mysql.sock}"
MYSQL_PID_FILE="${MYSQL_PID_FILE:-${LOCAL_HOME}/mysql.pid}"
MYSQL_LOG_FILE="${MYSQL_LOG_FILE:-${LOCAL_HOME}/mysql-error.log}"
MYSQL_PORT="${MYSQL_PORT:-3306}"
MYSQL_USER="${MYSQL_USER:-root}"
MYSQL_PASSWORD="${MYSQL_PASSWORD:-}"

JAVA_HOME="${JAVA_HOME:-${LOCAL_HOME}/temurin17/Contents/Home}"
MAVEN_HOME="${MAVEN_HOME:-${LOCAL_HOME}/maven}"
BACKEND_PORT="${BACKEND_PORT:-9000}"
FRONTEND_PORT="${FRONTEND_PORT:-3002}"
DB_NAME="${DB_NAME:-farm_energy}"

export JAVA_HOME
export PATH="${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${PATH}"

MYSQL="${MYSQL_HOME}/bin/mysql"
MYSQLD="${MYSQL_HOME}/bin/mysqld"
MYSQL_STARTED_MARKER="${ROOT_DIR}/scripts/.mysql-started-by-start-all"

log() {
  printf '[start-all] %s\n' "$1"
}

port_listening() {
  lsof -nP -iTCP:"$1" -sTCP:LISTEN >/dev/null 2>&1
}

wait_for_port() {
  local port="$1"
  local name="$2"
  local attempts="${3:-60}"

  for _ in $(seq 1 "$attempts"); do
    if port_listening "$port"; then
      log "${name} is ready on port ${port}"
      return 0
    fi
    sleep 1
  done

  log "${name} did not become ready on port ${port}"
  return 1
}

mysql_command() {
  if [[ -n "${MYSQL_PASSWORD}" ]]; then
    "${MYSQL}" -h127.0.0.1 -P"${MYSQL_PORT}" -u"${MYSQL_USER}" "-p${MYSQL_PASSWORD}" "$@"
  else
    "${MYSQL}" -h127.0.0.1 -P"${MYSQL_PORT}" -u"${MYSQL_USER}" "$@"
  fi
}

ensure_tools() {
  [[ -x "${MYSQL}" ]] || { log "mysql not found: ${MYSQL}"; exit 1; }
  [[ -x "${MYSQLD}" ]] || { log "mysqld not found: ${MYSQLD}"; exit 1; }
  [[ -x "${JAVA_HOME}/bin/java" ]] || { log "java not found: ${JAVA_HOME}/bin/java"; exit 1; }
  [[ -x "${MAVEN_HOME}/bin/mvn" ]] || { log "maven not found: ${MAVEN_HOME}/bin/mvn"; exit 1; }
}

start_mysql() {
  if port_listening "${MYSQL_PORT}"; then
    log "MySQL is already running on port ${MYSQL_PORT}"
    return
  fi

  [[ -d "${MYSQL_DATA_DIR}" ]] || {
    log "MySQL data directory not found: ${MYSQL_DATA_DIR}"
    log "Please initialize MySQL first, or set MYSQL_DATA_DIR to the correct directory."
    exit 1
  }

  log "Starting MySQL on port ${MYSQL_PORT}"
  "${MYSQLD}" \
    --basedir="${MYSQL_HOME}" \
    --datadir="${MYSQL_DATA_DIR}" \
    --port="${MYSQL_PORT}" \
    --bind-address=127.0.0.1 \
    --socket="${MYSQL_SOCKET}" \
    --pid-file="${MYSQL_PID_FILE}" \
    --log-error="${MYSQL_LOG_FILE}" \
    --daemonize

  : > "${MYSQL_STARTED_MARKER}"
  wait_for_port "${MYSQL_PORT}" "MySQL" 30
}

seed_mysql() {
  log "Preparing database ${DB_NAME}"
  mysql_command < "${ROOT_DIR}/backend-springboot/mysql-init.sql"
}

start_backend() {
  if port_listening "${BACKEND_PORT}"; then
    log "Backend is already running on port ${BACKEND_PORT}"
    return
  fi

  log "Starting Spring Boot backend on port ${BACKEND_PORT}"
  (
    cd "${ROOT_DIR}/backend-springboot"
    nohup env \
      DB_URL="jdbc:mariadb://127.0.0.1:${MYSQL_PORT}/${DB_NAME}?useUnicode=true&characterEncoding=UTF-8" \
      DB_USERNAME="${MYSQL_USER}" \
      DB_PASSWORD="${MYSQL_PASSWORD}" \
      SERVER_PORT="${BACKEND_PORT}" \
      "${MAVEN_HOME}/bin/mvn" spring-boot:run \
      > springboot.out.log 2> springboot.err.log &
    echo $! > .springboot.pid
  )

  wait_for_port "${BACKEND_PORT}" "Backend" 90
}

pick_frontend_port() {
  local port="${FRONTEND_PORT}"
  while port_listening "${port}"; do
    log "Frontend port ${port} is in use, trying $((port + 1))"
    port=$((port + 1))
  done
  printf '%s\n' "${port}"
}

start_frontend() {
  local port
  port="$(pick_frontend_port)"

  log "Starting Vue frontend on port ${port}"
  (
    cd "${ROOT_DIR}/client-vue"
    nohup npm run dev -- --host 0.0.0.0 --port "${port}" \
      > vite.out.log 2> vite.err.log &
    echo $! > .vite.pid
    echo "${port}" > .vite.port
  )

  wait_for_port "${port}" "Frontend" 45
  log "Frontend URL: http://localhost:${port}/"
  log "Backend URL:  http://localhost:${BACKEND_PORT}/api/..."
}

main() {
  ensure_tools
  start_mysql
  seed_mysql
  start_backend
  start_frontend
  log "All services started."
}

main "$@"
