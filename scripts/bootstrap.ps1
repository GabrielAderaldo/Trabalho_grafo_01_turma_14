# scripts/bootstrap.ps1 - Configuração Automática (Windows PowerShell)

# 1. Verificar dependências
Write-Host ">>> [STEP 1] Verificando Java..." -ForegroundColor Cyan
if (!(Get-Command java -ErrorAction SilentlyContinue) -or !(Get-Command javac -ErrorAction SilentlyContinue)) {
    Write-Host "ERRO: Java ou Javac não encontrados! Por favor, instale o JDK." -ForegroundColor Red
    exit
}

Write-Host ">>> [STEP 2] Verificando Git Submodules (algs4)..." -ForegroundColor Cyan
git submodule update --init --recursive

# 2. Verificar Task (Opcional mas Recomendado)
Write-Host ">>> [STEP 3] Verificando Task Runner..." -ForegroundColor Cyan
if (!(Get-Command task -ErrorAction SilentlyContinue)) {
    Write-Host "DICA: O Task não está instalado. Recomendado: 'choco install go-task' ou 'scoop install task'" -ForegroundColor Yellow
    $RUNNER = "make"
} else {
    $RUNNER = "task"
}

# 3. Configuração inicial (Setup e Geração)
Write-Host ">>> [STEP 4] Compilando e Gerando Dataset do Facebook..." -ForegroundColor Cyan
& $RUNNER setup
& $RUNNER generate

# 4. Configuração do Python e Jupyter (Data Science)
Write-Host ">>> [STEP 5] Verificando Ambiente Python..." -ForegroundColor Cyan
if (!(Get-Command python -ErrorAction SilentlyContinue)) {
    Write-Host "DICA: Python não encontrado. Por favor, instale o Python 3." -ForegroundColor Yellow
} else {
    if (!(Test-Path "venv")) {
        Write-Host ">>> Criando ambiente virtual (venv)..." -ForegroundColor Cyan
        python -m venv venv
    }
    
    # Ativação do venv no Windows
    & .\venv\Scripts\Activate.ps1
    Write-Host ">>> Instalando dependências do Notebook (Pandas, Jupyter, etc.)..." -ForegroundColor Cyan
    pip install --quiet -r requirements.txt
    Write-Host ">>> [OK] Ambiente Python configurado!" -ForegroundColor Green
}

Write-Host ">>> [SUCCESS] Projeto configurado com sucesso!" -ForegroundColor Green
Write-Host ">>> Para o Java: rodar '$RUNNER run-full'" -ForegroundColor Green
Write-Host ">>> Para o Notebook: rodar 'venv\Scripts\Activate.ps1; jupyter-lab'" -ForegroundColor Green
