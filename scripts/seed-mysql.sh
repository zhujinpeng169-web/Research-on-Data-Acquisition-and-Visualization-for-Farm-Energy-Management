#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
LOCAL_HOME="${HOME}/.local"

MYSQL_HOME="${MYSQL_HOME:-${LOCAL_HOME}/tools/mysql-8.4.9-macos15-arm64}"
MYSQL_PORT="${MYSQL_PORT:-3306}"
MYSQL_USER="${MYSQL_USER:-root}"
MYSQL_PASSWORD="${MYSQL_PASSWORD:-}"
DB_NAME="${DB_NAME:-farm_energy}"
MYSQL="${MYSQL_HOME}/bin/mysql"

log() {
  printf '[seed-mysql] %s\n' "$1"
}

mysql_command() {
  if [[ -n "${MYSQL_PASSWORD}" ]]; then
    "${MYSQL}" -h127.0.0.1 -P"${MYSQL_PORT}" -u"${MYSQL_USER}" "-p${MYSQL_PASSWORD}" "$@"
  else
    "${MYSQL}" -h127.0.0.1 -P"${MYSQL_PORT}" -u"${MYSQL_USER}" "$@"
  fi
}

usage() {
  cat <<USAGE
Usage:
  ./scripts/seed-mysql.sh          Apply mysql-init.sql safely.
  ./scripts/seed-mysql.sh --reset  Drop ${DB_NAME}, recreate tables, then let backend seed demo data on next startup.
USAGE
}

main() {
  [[ -x "${MYSQL}" ]] || { log "mysql not found: ${MYSQL}"; exit 1; }

  local reset=0
  if [[ "${1:-}" == "--help" || "${1:-}" == "-h" ]]; then
    usage
    exit 0
  elif [[ "${1:-}" == "--reset" ]]; then
    reset=1
  elif [[ -n "${1:-}" ]]; then
    usage
    exit 1
  fi

  if [[ "${reset}" == "1" ]]; then
    log "Dropping database ${DB_NAME}"
    mysql_command -e "DROP DATABASE IF EXISTS \`${DB_NAME}\`;"
  fi

  log "Applying backend-springboot/mysql-init.sql"
  mysql_command < "${ROOT_DIR}/backend-springboot/mysql-init.sql"
  log "Database is ready: ${DB_NAME}"
}

main "$@"
