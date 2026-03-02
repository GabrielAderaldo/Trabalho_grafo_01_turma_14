#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
SRC_DIR="${ROOT_DIR}/src"
BUILD_DIR="${ROOT_DIR}/build"
ALGS4_SRC_DIR="${ROOT_DIR}/lib/algs4/src/main/java"
ALGS4_CLASSES_DIR="${BUILD_DIR}/algs4-classes"

echo "[windows/setup] Raiz do projeto: ${ROOT_DIR}"

if [ ! -d "${ALGS4_SRC_DIR}" ]; then
  echo "ERRO: biblioteca algs4 nao encontrada em ${ALGS4_SRC_DIR}."
  echo "Certifique-se de ter rodado: git submodule update --init --recursive"
  exit 1
fi

mkdir -p "${ALGS4_CLASSES_DIR}"

echo "[windows/setup] Compilando algs4 para ${ALGS4_CLASSES_DIR}..."
find "${ALGS4_SRC_DIR}" -type f -name '*.java' -print0 | \
  xargs -0 javac -d "${ALGS4_CLASSES_DIR}"

echo "[windows/setup] algs4 compilada em ${ALGS4_CLASSES_DIR}"

