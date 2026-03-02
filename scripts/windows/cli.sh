#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
SRC_DIR="${ROOT_DIR}/src"
BUILD_DIR="${ROOT_DIR}/build"
CLASSES_DIR="${BUILD_DIR}/classes"
ALGS4_SRC_DIR="${ROOT_DIR}/lib/algs4/src/main/java"
ALGS4_CLASSES_DIR="${BUILD_DIR}/algs4-classes"

JAVA_BIN="${JAVA:-java}"
JAVAC_BIN="${JAVAC:-javac}"
MAIN_CLASS="${MAIN:-Main}"
DEV_MAIN_CLASS="${DEV_MAIN:-app.Main}"

# Em Windows, a JVM usa ';' como separador de classpath.
CLASSPATH_SEPARATOR=';'

classpath_app() {
  printf "%s%s%s" "${ALGS4_CLASSES_DIR}" "${CLASSPATH_SEPARATOR}" "${CLASSES_DIR}"
}

ensure_algs4() {
  if [ ! -d "${ALGS4_SRC_DIR}" ]; then
    echo "ERRO: biblioteca algs4 nao encontrada em ${ALGS4_SRC_DIR}."
    echo "Certifique-se de ter rodado: git submodule update --init --recursive"
    exit 1
  fi

  if [ -d "${ALGS4_CLASSES_DIR}" ] && find "${ALGS4_CLASSES_DIR}" -type f -name '*.class' | head -n 1 >/dev/null 2>&1; then
    return 0
  fi

  mkdir -p "${ALGS4_CLASSES_DIR}"
  echo "[cli] Compilando algs4 para ${ALGS4_CLASSES_DIR}..."
  find "${ALGS4_SRC_DIR}" -type f -name '*.java' -print0 | \
    xargs -0 "${JAVAC_BIN}" -d "${ALGS4_CLASSES_DIR}"
  echo "[cli] algs4 compilada em ${ALGS4_CLASSES_DIR}"
}

ensure_app_classes() {
  ensure_algs4

  mkdir -p "${CLASSES_DIR}"

  mapfile -t APP_SOURCES < <(find "${SRC_DIR}" -type f -name '*.java')

  if [ "${#APP_SOURCES[@]}" -eq 0 ]; then
    echo "Nenhum arquivo .java encontrado em ${SRC_DIR}/"
    exit 0
  fi

  echo "[cli] Compilando codigo da aplicacao para ${CLASSES_DIR}..."
  "${JAVAC_BIN}" -cp "${ALGS4_CLASSES_DIR}" -d "${CLASSES_DIR}" "${APP_SOURCES[@]}"
  echo "[cli] Codigo compilado em ${CLASSES_DIR}"
}

cmd_help() {
  cat <<EOF
CLI (Windows/Bash) - comandos equivalentes ao Makefile:

  bash SCRIPTS/windows/cli.sh help
      Mostra esta ajuda.

  bash SCRIPTS/windows/cli.sh setup
      Compila a biblioteca algs4 para build/algs4-classes.

  bash SCRIPTS/windows/cli.sh classes
      Compila seu codigo Java em src/ para build/classes.

  bash SCRIPTS/windows/cli.sh run [ARGS...]
      Executa a classe principal (default: Main).
      Use MAIN=SeuMain para mudar a classe.

  DATA=arquivo.txt bash SCRIPTS/windows/cli.sh run-data [ARGS...]
      Executa a classe principal lendo de um arquivo de entrada.

  bash SCRIPTS/windows/cli.sh dev [ARGS...]
      Atalho para compilar e executar (default: app.Main).

  bash SCRIPTS/windows/cli.sh test
      Executa tests.EdgeListConverterTest.

  bash SCRIPTS/windows/cli.sh test-all
      Executa TODOS os testes unitarios conhecidos.

  bash SCRIPTS/windows/cli.sh generate
      Gera data/generated/facebook_union.txt.

  bash SCRIPTS/windows/cli.sh clean
      Remove a pasta build/.
EOF
}

cmd_check_algs4() {
  if [ -d "${ALGS4_SRC_DIR}" ]; then
    echo "algs4 encontrada em ${ALGS4_SRC_DIR}"
  else
    echo "ERRO: biblioteca algs4 nao encontrada em ${ALGS4_SRC_DIR}."
    echo "Certifique-se de ter rodado: git submodule update --init --recursive"
    exit 1
  fi
}

cmd_setup() {
  ensure_algs4
}

cmd_classes() {
  ensure_app_classes
}

cmd_run() {
  ensure_app_classes

  mapfile -t APP_SOURCES < <(find "${SRC_DIR}" -type f -name '*.java')
  if [ "${#APP_SOURCES[@]}" -eq 0 ]; then
    echo "Nada para executar: adicione arquivos .java em ${SRC_DIR}/"
    exit 1
  fi

  local cp
  cp="$(classpath_app)"
  echo "[cli] Executando ${MAIN_CLASS}..."
  (cd "${ROOT_DIR}" && "${JAVA_BIN}" -cp "${cp}" "${MAIN_CLASS}" "$@")
}

cmd_run_data() {
  ensure_app_classes

  if [ -z "${DATA:-}" ]; then
    echo "Informe DATA=arquivo.txt"
    exit 1
  fi
  if [ ! -f "${DATA}" ]; then
    echo "Arquivo nao encontrado: ${DATA}"
    exit 1
  fi

  local cp
  cp="$(classpath_app)"
  echo "[cli] Executando ${MAIN_CLASS} com entrada de ${DATA}..."
  (cd "${ROOT_DIR}" && "${JAVA_BIN}" -cp "${cp}" "${MAIN_CLASS}" "$@" < "${DATA}")
}

cmd_dev() {
  MAIN_CLASS="${DEV_MAIN_CLASS}"
  cmd_run "$@"
}

cmd_test() {
  ensure_app_classes
  local cp
  cp="$(classpath_app)"
  echo "[cli] Executando tests.EdgeListConverterTest..."
  (cd "${ROOT_DIR}" && "${JAVA_BIN}" -cp "${cp}" tests.EdgeListConverterTest)
}

cmd_test_all() {
  ensure_app_classes
  local cp
  cp="$(classpath_app)"

  echo "Executando suite de testes completa..."

  local tests=(
    tests.DegreeStatsTest
    tests.ConnectivityTest
    tests.BipartiteTest
    tests.AdjListTest
    tests.AdjacencyMatrixTest
    tests.IncidenceMatrixTest
    tests.EdgeListTest
    tests.CSRTest
    tests.AdjMatrixBinaryTest
    tests.IncidenceBinaryTest
    tests.EdgeListBinaryTest
    tests.RawDegreeExportTest
    tests.DataExporterTest
  )

  (cd "${ROOT_DIR}" && for t in "${tests[@]}"; do
    echo "[cli][test-all] Executando ${t}..."
    "${JAVA_BIN}" -cp "${cp}" "${t}" || true
  done)
}

cmd_generate() {
  ensure_app_classes
  local cp
  cp="$(classpath_app)"
  echo "Iniciando geracao do dataset para algs4..."
  (cd "${ROOT_DIR}" && "${JAVA_BIN}" -cp "${cp}" utils.EdgeListConverter)
}

cmd_clean() {
  rm -rf "${BUILD_DIR}"
  echo "Removido ${BUILD_DIR}/"
}

main() {
  local cmd="${1:-help}"
  shift || true

  case "${cmd}" in
    help)        cmd_help "$@" ;;
    check-algs4) cmd_check_algs4 "$@" ;;
    setup)       cmd_setup "$@" ;;
    classes)     cmd_classes "$@" ;;
    run)         cmd_run "$@" ;;
    run-data)    cmd_run_data "$@" ;;
    dev)         cmd_dev "$@" ;;
    test)        cmd_test "$@" ;;
    test-all)    cmd_test_all "$@" ;;
    generate)    cmd_generate "$@" ;;
    clean)       cmd_clean "$@" ;;
    *)
      echo "Comando desconhecido: ${cmd}"
      echo "Use: bash SCRIPTS/windows/cli.sh help"
      exit 1
      ;;
  esac
}

main "$@"

