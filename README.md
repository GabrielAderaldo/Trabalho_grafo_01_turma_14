# Trabalho de Grafos - Turma 14

Repositorio da disciplina para analise de grafos com dataset do SNAP (Facebook ego networks) e biblioteca `algs4` (Princeton).

## Visao geral

Este repositorio contem:
- dataset em `data/facebook`;
- requisitos do trabalho em `handbook/requesitos_trabalho`;
- biblioteca `algs4` como submodule em `lib/algs4`;
- automacao de compilacao/execucao com `Makefile`;
- ambiente padrao com Docker (`Dockerfile`).

## Pre-requisitos (local)

Instale:
- `git`
- `make`
- `java`/`javac` (JDK 17+ recomendado)

Validacao:

```bash
git --version
make --version
java -version
javac -version
```

## Clone correto (obrigatorio)

A `algs4` e um **git submodule**. Se nao baixar submodule, nao compila.

Clone recomendado:

```bash
git clone --recurse-submodules <URL_DO_REPO>
cd tg01_14
```

Se ja clonou sem submodule:

```bash
git submodule update --init --recursive
```

## Estrutura principal

```text
.
├── data/
│   ├── readme.md
│   └── facebook/
├── handbook/
│   └── requesitos_trabalho/
├── lib/
│   └── algs4/
├── src/
│   └── main.java
├── Makefile
└── Dockerfile
```

## Inicio rapido (jeito mais simples)

Com o projeto clonado corretamente:

```bash
make dev
```

Isso compila `algs4`, compila seu codigo em `src/` e executa a classe configurada no modo dev.

No estado atual do repo, o `dev` executa `main` (arquivo `src/main.java`).

## Comandos do Makefile

### `make help`
Mostra todos os targets.

### `make setup`
Compila `algs4` para `build/algs4-classes`.

### `make classes`
Compila arquivos `.java` de `src/` para `build/classes`.

### `make dev`
Atalho estilo `npm run dev`.

Por padrao executa `DEV_MAIN=main`.

Exemplo com argumentos:

```bash
make dev ARGS="--ego 0 --edges data/facebook/0.edges"
```

### `make run MAIN=...`
Executa uma classe especifica.

Exemplos:

```bash
make run MAIN=main
make run MAIN=Main
make run MAIN=br.unifor.grafos.Main
```

### `make run-data MAIN=... DATA=...`
Executa classe lendo entrada via `stdin`.

Exemplo:

```bash
make run-data MAIN=main DATA=data/facebook/0.edges
```

### `make clean`
Remove pasta `build/`.

## Usando Docker

### Build da imagem

```bash
docker build -t tg01_14-java .
```

### Rodar com bind mount (recomendado para desenvolvimento)

```bash
docker run --rm -it -v "$PWD":/app -w /app tg01_14-java make dev
```

Outros exemplos:

```bash
docker run --rm -it -v "$PWD":/app -w /app tg01_14-java make classes
docker run --rm -it -v "$PWD":/app -w /app tg01_14-java make run MAIN=main
```

## Dataset (`data/facebook`)

Para cada `nodeId`, existem 5 arquivos:
- `nodeId.edges`: arestas entre amigos do ego (Facebook: nao-direcionado).
- `nodeId.circles`: circulos (ground-truth).
- `nodeId.feat`: features binarias dos nos.
- `nodeId.egofeat`: features binarias do ego.
- `nodeId.featnames`: nomes das dimensoes de feature (anonimizadas no Facebook).

## Regras para a equipe

- Coloque seu codigo Java em `src/`.
- Nao altere `data/facebook`.
- Nao altere manualmente `lib/algs4` (dependencia externa via submodule).
- Nao versione artefatos de build (`build/`).

## Problemas comuns

### Erro: `algs4 nao encontrada`

```bash
git submodule update --init --recursive
```

### Erro: `Nenhum arquivo .java encontrado em src/`

Crie arquivos `.java` em `src/`.

### Erro: `Could not find or load main class ...`

Confira exatamente o nome da classe:
- classe atual do repo: `main` (minusculo)
- se criar outra classe, ajuste `MAIN`/`DEV_MAIN` no comando

Exemplo:

```bash
make run MAIN=main
make dev DEV_MAIN=Main
```

## Referencias

- SNAP: https://snap.stanford.edu/data/
- algs4: https://github.com/kevin-wayne/algs4
