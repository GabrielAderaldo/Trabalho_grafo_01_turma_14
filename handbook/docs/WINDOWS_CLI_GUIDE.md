## CLI para Windows - Uso e Diferenças de Setup

Este projeto foi pensado originalmente para um ambiente Unix-like (Linux/macOS) e usa `Makefile` com comandos como `test`, `find`, `xargs` e `rm`. Em Windows puro (cmd/PowerShell), esses comandos não existem, então foi criado um **CLI em Bash específico para Windows**.

- **Script principal**: `SCRIPTS/windows/cli.sh`
- **Requisito**: rodar em um shell com suporte a Bash (por exemplo, **Git Bash** ou **WSL**).

### Comandos disponíveis (equivalentes ao Makefile)

Todos os comandos abaixo devem ser executados a partir da raiz do projeto:

```bash
bash SCRIPTS/windows/cli.sh <comando> [args...]
```

- **help**: mostra a ajuda geral.
  - `bash SCRIPTS/windows/cli.sh help`
- **setup**: compila a biblioteca `algs4` para `build/algs4-classes`.
  - `bash SCRIPTS/windows/cli.sh setup`
- **classes**: compila o código Java em `src/` para `build/classes`.
  - `bash SCRIPTS/windows/cli.sh classes`
- **run**: executa a classe principal (default: `Main`).
  - `bash SCRIPTS/windows/cli.sh run`
  - `MAIN=app.Main bash SCRIPTS/windows/cli.sh run` (equivalente a `make run MAIN=app.Main`)
- **run-data**: executa a classe principal lendo de um arquivo de entrada.
  - `DATA=data/arquivo.txt bash SCRIPTS/windows/cli.sh run-data`
- **dev**: atalho para compilar e executar (default: `app.Main`).
  - `bash SCRIPTS/windows/cli.sh dev`
- **test**: executa `tests.EdgeListConverterTest`.
  - `bash SCRIPTS/windows/cli.sh test`
- **test-all**: executa toda a suíte de testes definida no `Makefile`.
  - `bash SCRIPTS/windows/cli.sh test-all`
- **generate**: gera `data/generated/facebook_union.txt` (equivalente a `make generate`).
  - `bash SCRIPTS/windows/cli.sh generate`
- **clean**: remove a pasta `build/`.
  - `bash SCRIPTS/windows/cli.sh clean`

### Diferenças de setup por sistema operacional

#### Linux / macOS (Unix-like)

- Ferramentas como `test`, `find`, `xargs`, `rm` e `bash` já existem no sistema.
- O `Makefile` pode ser usado diretamente:

```bash
make setup
make classes
make run MAIN=app.Main
make generate
make test-all
```

- O separador de classpath na JVM é `:` (dois pontos), o que já está refletido no `Makefile`.

#### Windows

Atenção para três pontos principais:

- **Shell**:
  - `cmd` e `PowerShell` **não** fornecem os comandos Unix usados pelo `Makefile`.
  - Use **Git Bash** ou **WSL** para executar o CLI (`cli.sh`).

- **Classpath**:
  - No Windows, a JVM usa `;` (ponto e vírgula) como separador de classpath.
  - O script `SCRIPTS/windows/cli.sh` já ajusta automaticamente o classpath para o formato correto (`build/algs4-classes;build/classes`).

- **Submódulo `algs4`**:
  - Antes de rodar `setup` (seja via `make` ou via CLI), certifique-se de inicializar o submódulo:

```bash
git submodule update --init --recursive
```

Sem isso, nem o `Makefile` (Unix) nem o CLI (Windows) conseguirão compilar a biblioteca `algs4`.

