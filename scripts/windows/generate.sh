#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
BUILD_DIR="${ROOT_DIR}/build"
CLASSES_DIR="${BUILD_DIR}/classes"
ALGS4_CLASSES_DIR="${BUILD_DIR}/algs4-classes"

echo "[windows/generate] Raiz do projeto: ${ROOT_DIR}"

if [ ! -d "${ALGS4_CLASSES_DIR}" ]; then
  echo "[windows/generate] algs4 ainda nao foi compilada. Rodando setup..."
  "${ROOT_DIR}/SCRIPTS/windows/setup.sh"
fi

if [ ! -d "${CLASSES_DIR}" ]; then
  echo "[windows/generate] Codigo da aplicacao ainda nao foi compilado. Rodando classes..."
  "${ROOT_DIR}/SCRIPTS/windows/classes.sh"
fi

echo "Iniciando geracao do dataset para algs4..."
cd "${ROOT_DIR}"
java -cp "${ALGS4_CLASSES_DIR}:${CLASSES_DIR}" utils.EdgeListConverter

