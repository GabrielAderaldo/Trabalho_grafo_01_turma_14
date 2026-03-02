#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
SRC_DIR="${ROOT_DIR}/src"
BUILD_DIR="${ROOT_DIR}/build"
CLASSES_DIR="${BUILD_DIR}/classes"
ALGS4_CLASSES_DIR="${BUILD_DIR}/algs4-classes"

echo "[windows/classes] Raiz do projeto: ${ROOT_DIR}"

if [ ! -d "${ALGS4_CLASSES_DIR}" ]; then
  echo "[windows/classes] algs4 ainda nao foi compilada. Rodando setup..."
  "${ROOT_DIR}/SCRIPTS/windows/setup.sh"
fi

mkdir -p "${CLASSES_DIR}"

mapfile -t APP_SOURCES < <(find "${SRC_DIR}" -type f -name '*.java')

if [ "${#APP_SOURCES[@]}" -eq 0 ]; then
  echo "Nenhum arquivo .java encontrado em ${SRC_DIR}/"
  exit 0
fi

echo "[windows/classes] Compilando codigo da aplicacao para ${CLASSES_DIR}..."
javac -cp "${ALGS4_CLASSES_DIR}" -d "${CLASSES_DIR}" "${APP_SOURCES[@]}"

echo "[windows/classes] Codigo compilado em ${CLASSES_DIR}"

