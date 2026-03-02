#!/bin/bash

# scripts/bootstrap.sh - Configuração Automática (Mac/Linux/WSL)

# 1. Verificar dependências
echo ">>> [STEP 1] Verificando Java..."
if ! command -v java &> /dev/null || ! command -v javac &> /dev/null; then
    echo "ERRO: Java ou Javac não encontrados! Por favor, instale o JDK."
    exit 1
fi

echo ">>> [STEP 2] Verificando Git Submodules (algs4)..."
git submodule update --init --recursive 2>/dev/null || echo "Aviso: Nao foi possivel atualizar submodules (ignorar se a pasta lib/algs4 ja existir)."

# 2. Verificar Task (Opcional mas Recomendado)
echo ">>> [STEP 3] Verificando Task Runner..."
if ! command -v task &> /dev/null; then
    echo "DICA: O Task não está instalado. Recomendado: 'brew install go-task'"
    echo "Usando 'make' como fallback para a configuracao inicial..."
    RUNNER="make"
else
    RUNNER="task"
fi

# 3. Configuração inicial (Setup e Geração)
echo ">>> [STEP 4] Compilando e Gerando Dataset do Facebook..."
$RUNNER setup
$RUNNER generate

# 4. Configuração do Python e Jupyter (Data Science)
echo ">>> [STEP 5] Verificando Ambiente Python..."
if ! command -v python3 &> /dev/null; then
    echo "DICA: Python 3 não encontrado. Por favor, instale o Python 3."
else
    if [ ! -d "venv" ]; then
        echo ">>> Criando ambiente virtual (venv)..."
        python3 -m venv venv
    fi
    
    source venv/bin/activate
    echo ">>> Instalando dependências do Notebook (Pandas, Jupyter, etc.)..."
    pip install --quiet -r requirements.txt
    echo ">>> [OK] Ambiente Python configurado!"
fi

echo ">>> [SUCCESS] Projeto configurado com sucesso!"
echo ">>> Para o Java: rodar '$RUNNER run-full'"
echo ">>> Para o Notebook: rodar 'source venv/bin/activate && jupyter-lab'"
