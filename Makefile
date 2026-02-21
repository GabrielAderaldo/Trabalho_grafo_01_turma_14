SHELL := /bin/bash

SRC_DIR := src
BUILD_DIR := build
CLASSES_DIR := $(BUILD_DIR)/classes
ALGS4_SRC_DIR := lib/algs4/src/main/java
ALGS4_CLASSES_DIR := $(BUILD_DIR)/algs4-classes

JAVA ?= java
JAVAC ?= javac
MAIN ?= Main
DEV_MAIN ?= main
ARGS ?=

APP_SOURCES := $(shell find $(SRC_DIR) -type f -name '*.java' 2>/dev/null)

.PHONY: help check-algs4 setup classes run run-data dev clean

help:
	@echo "Targets disponiveis:"
	@echo "  make setup                 # compila a biblioteca algs4 para build/algs4-classes"
	@echo "  make classes               # compila seu codigo Java em src/ para build/classes"
	@echo "  make dev                   # atalho para compilar e executar (default: main)"
	@echo "  make run MAIN=SeuMain      # executa a classe principal (default: Main)"
	@echo "  make run-data MAIN=SeuMain DATA=arquivo.txt"
	@echo "  make clean                 # remove pasta build/"

check-algs4:
	@test -d "$(ALGS4_SRC_DIR)" || \
		(echo "ERRO: biblioteca algs4 nao encontrada em $(ALGS4_SRC_DIR)." && \
		 echo "Execute: git submodule update --init --recursive" && exit 1)

setup: $(ALGS4_CLASSES_DIR)/.compiled

$(ALGS4_CLASSES_DIR)/.compiled: check-algs4
	@mkdir -p "$(ALGS4_CLASSES_DIR)"
	@find "$(ALGS4_SRC_DIR)" -type f -name '*.java' -print0 | \
		xargs -0 $(JAVAC) -d "$(ALGS4_CLASSES_DIR)"
	@touch "$(ALGS4_CLASSES_DIR)/.compiled"
	@echo "algs4 compilada em $(ALGS4_CLASSES_DIR)"

classes: setup
	@mkdir -p "$(CLASSES_DIR)"
	@if [ -z "$(APP_SOURCES)" ]; then \
		echo "Nenhum arquivo .java encontrado em $(SRC_DIR)/"; \
	else \
		$(JAVAC) -cp "$(ALGS4_CLASSES_DIR)" -d "$(CLASSES_DIR)" $(APP_SOURCES); \
		echo "Codigo compilado em $(CLASSES_DIR)"; \
	fi

run: classes
	@if [ -z "$(APP_SOURCES)" ]; then \
		echo "Nada para executar: adicione arquivos .java em $(SRC_DIR)/"; \
		exit 1; \
	fi
	@$(JAVA) -cp "$(ALGS4_CLASSES_DIR):$(CLASSES_DIR)" "$(MAIN)" $(ARGS)

run-data: classes
	@test -n "$(DATA)" || (echo "Informe DATA=arquivo.txt"; exit 1)
	@test -f "$(DATA)" || (echo "Arquivo nao encontrado: $(DATA)"; exit 1)
	@$(JAVA) -cp "$(ALGS4_CLASSES_DIR):$(CLASSES_DIR)" "$(MAIN)" $(ARGS) < "$(DATA)"

dev:
	@$(MAKE) run MAIN="$(DEV_MAIN)" ARGS="$(ARGS)"

clean:
	@rm -rf "$(BUILD_DIR)"
	@echo "Removido $(BUILD_DIR)/"
