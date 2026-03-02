Param(
    [string]$Command = "help",
    [string[]]$Rest
)

$ErrorActionPreference = "Stop"

$RootDir = (Resolve-Path "$PSScriptRoot\..\..").Path
$SrcDir = Join-Path $RootDir "src"
$BuildDir = Join-Path $RootDir "build"
$ClassesDir = Join-Path $BuildDir "classes"
$Algs4SrcDir = Join-Path $RootDir "lib\algs4\src\main\java"
$Algs4ClassesDir = Join-Path $BuildDir "algs4-classes"

$JavaBin = if ($env:JAVA) { $env:JAVA } else { "java" }
$JavacBin = if ($env:JAVAC) { $env:JAVAC } else { "javac" }
$MainClass = if ($env:MAIN) { $env:MAIN } else { "Main" }
$DevMainClass = if ($env:DEV_MAIN) { $env:DEV_MAIN } else { "app.Main" }

function Get-AppClasspath {
    return "$Algs4ClassesDir;$ClassesDir"
}

function Ensure-Algs4 {
    if (-not (Test-Path $Algs4SrcDir -PathType Container)) {
        Write-Host "ERRO: biblioteca algs4 nao encontrada em $Algs4SrcDir."
        Write-Host "Certifique-se de ter rodado: git submodule update --init --recursive"
        exit 1
    }

    if (Test-Path $Algs4ClassesDir -PathType Container -ErrorAction SilentlyContinue) {
        $existing = Get-ChildItem -Path $Algs4ClassesDir -Recurse -Filter *.class -ErrorAction SilentlyContinue | Select-Object -First 1
        if ($existing) {
            return
        }
    }

    New-Item -ItemType Directory -Force -Path $Algs4ClassesDir | Out-Null
    Write-Host "[cli.ps1] Compilando algs4 para $Algs4ClassesDir..."

    $sources = Get-ChildItem -Recurse -Path $Algs4SrcDir -Filter *.java | ForEach-Object { $_.FullName }
    if (-not $sources -or $sources.Count -eq 0) {
        Write-Host "Nenhum arquivo .java encontrado em $Algs4SrcDir"
        exit 1
    }

    & $JavacBin -d $Algs4ClassesDir @sources
    Write-Host "[cli.ps1] algs4 compilada em $Algs4ClassesDir"
}

function Ensure-AppClasses {
    Ensure-Algs4

    New-Item -ItemType Directory -Force -Path $ClassesDir | Out-Null

    $sources = Get-ChildItem -Recurse -Path $SrcDir -Filter *.java | ForEach-Object { $_.FullName }
    if (-not $sources -or $sources.Count -eq 0) {
        Write-Host "Nenhum arquivo .java encontrado em $SrcDir/"
        exit 0
    }

    Write-Host "[cli.ps1] Compilando codigo da aplicacao para $ClassesDir..."
    & $JavacBin -cp $Algs4ClassesDir -d $ClassesDir @sources
    Write-Host "[cli.ps1] Codigo compilado em $ClassesDir"
}

function Cmd-Help {
    @"
CLI PowerShell (Windows) - comandos equivalentes ao Makefile

Uso geral:
  pwsh -File SCRIPTS/windows/cli.ps1 <comando> [args...]

Comandos:
  help             Mostra esta ajuda.
  setup            Compila a biblioteca algs4 para build/algs4-classes.
  classes          Compila seu codigo Java em src/ para build/classes.
  run              Executa a classe principal (default: Main).
                   Use MAIN=SeuMain no ambiente para mudar a classe.
  run-data         Executa a classe principal lendo de um arquivo DATA.
                   Use DATA=arquivo.txt no ambiente.
  dev              Atalho para compilar e executar (default: app.Main).
  test             Executa tests.EdgeListConverterTest.
  test-all         Executa TODOS os testes unitarios.
  generate         Gera data/generated/facebook_union.txt.
  clean            Remove a pasta build/.
"@ | Write-Host
}

function Cmd-CheckAlgs4 {
    if (Test-Path $Algs4SrcDir -PathType Container) {
        Write-Host "algs4 encontrada em $Algs4SrcDir"
    }
    else {
        Write-Host "ERRO: biblioteca algs4 nao encontrada em $Algs4SrcDir."
        Write-Host "Certifique-se de ter rodado: git submodule update --init --recursive"
        exit 1
    }
}

function Cmd-Setup {
    Ensure-Algs4
}

function Cmd-Classes {
    Ensure-AppClasses
}

function Cmd-Run {
    Ensure-AppClasses

    $sources = Get-ChildItem -Recurse -Path $SrcDir -Filter *.java | Select-Object -First 1
    if (-not $sources) {
        Write-Host "Nada para executar: adicione arquivos .java em $SrcDir/"
        exit 1
    }

    $cp = Get-AppClasspath
    Write-Host "[cli.ps1] Executando $MainClass..."
    Push-Location $RootDir
    & $JavaBin -cp $cp $MainClass @Rest
    Pop-Location
}

function Cmd-RunData {
    Ensure-AppClasses

    $data = $env:DATA
    if (-not $data) {
        Write-Host "Informe DATA=arquivo.txt"
        exit 1
    }
    if (-not (Test-Path $data -PathType Leaf)) {
        Write-Host "Arquivo nao encontrado: $data"
        exit 1
    }

    $cp = Get-AppClasspath
    Write-Host "[cli.ps1] Executando $MainClass com entrada de $data..."
    Push-Location $RootDir
    Get-Content $data | & $JavaBin -cp $cp $MainClass @Rest
    Pop-Location
}

function Cmd-Dev {
    $script:MainClass = $DevMainClass
    Cmd-Run
}

function Cmd-Test {
    Ensure-AppClasses
    $cp = Get-AppClasspath
    Write-Host "[cli.ps1] Executando tests.EdgeListConverterTest..."
    Push-Location $RootDir
    & $JavaBin -cp $cp tests.EdgeListConverterTest
    Pop-Location
}

function Cmd-TestAll {
    Ensure-AppClasses
    $cp = Get-AppClasspath

    $tests = @(
        "tests.DegreeStatsTest",
        "tests.ConnectivityTest",
        "tests.BipartiteTest",
        "tests.AdjListTest",
        "tests.AdjacencyMatrixTest",
        "tests.IncidenceMatrixTest",
        "tests.EdgeListTest",
        "tests.CSRTest",
        "tests.AdjMatrixBinaryTest",
        "tests.IncidenceBinaryTest",
        "tests.EdgeListBinaryTest",
        "tests.RawDegreeExportTest",
        "tests.DataExporterTest"
    )

    Write-Host "Executando suite de testes completa..."

    Push-Location $RootDir
    foreach ($t in $tests) {
        Write-Host "[cli.ps1][test-all] Executando $t..."
        try {
            & $JavaBin -cp $cp $t
        }
        catch {
            # Mantem o comportamento do Makefile com '|| true'
            Write-Host "[cli.ps1][test-all] Teste falhou: $t"
        }
    }
    Pop-Location
}

function Cmd-Generate {
    Ensure-AppClasses
    $cp = Get-AppClasspath
    Write-Host "Iniciando geracao do dataset para algs4..."
    Push-Location $RootDir
    & $JavaBin -cp $cp utils.EdgeListConverter
    Pop-Location
}

function Cmd-Clean {
    if (Test-Path $BuildDir) {
        Remove-Item -Recurse -Force $BuildDir
        Write-Host "Removido $BuildDir/"
    }
    else {
        Write-Host "Nenhuma pasta build/ encontrada."
    }
}

switch ($Command) {
    "help"        { Cmd-Help }
    "check-algs4" { Cmd-CheckAlgs4 }
    "setup"       { Cmd-Setup }
    "classes"     { Cmd-Classes }
    "run"         { Cmd-Run }
    "run-data"    { Cmd-RunData }
    "dev"         { Cmd-Dev }
    "test"        { Cmd-Test }
    "test-all"    { Cmd-TestAll }
    "generate"    { Cmd-Generate }
    "clean"       { Cmd-Clean }
    Default {
        Write-Host "Comando desconhecido: $Command"
        Write-Host "Use: pwsh -File SCRIPTS/windows/cli.ps1 help"
        exit 1
    }
}

