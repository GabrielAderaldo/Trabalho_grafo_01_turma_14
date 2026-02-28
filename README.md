# Trabalho de Grafos - Turma 14

Repositorio da disciplina para analise de grafos com o dataset do SNAP (Facebook ego networks) e a biblioteca `algs4` (Princeton).

---

## 🏗️ Estrutura do Projeto

O projeto está organizado seguindo padrões de engenharia de software:

```text
.
├── data/
│   ├── facebook/               # Dataset original do SNAP
│   ├── union_data_facebook/    # Arquivo de união (entrada)
│   └── generated/              # Arquivos processados e exportações binárias
├── handbook/
│   ├── backlog/                # Cards JIRA com tarefas pendentes e concluídas
│   ├── docs/                   # Referências técnicas e tutoriais
│   └── requirements/           # Requisitos pedagógicos do professor
├── src/
│   ├── app/                    # Código principal (Main e FacebookGraph Wrapper)
│   ├── utils/                  # Utilitários (EdgeListConverter)
│   └── tests/                  # Suite de testes unitários (Critérios de Aceite)
├── Makefile                    # Automação de compilação e testes
└── README.md                   # Este guia
```

---

## 🚀 Fluxo de Trabalho (Para a Equipe)

Este projeto utiliza **TDD (Test-Driven Development)**. Cada funcionalidade do trabalho possui um teste unitário e um card no backlog.

### 1. Preparação dos Dados
Antes de qualquer coisa, você precisa gerar o arquivo que a biblioteca `algs4` consegue ler:
```bash
make generate
```
Isso criará o arquivo `data/generated/facebook_union.txt` com o cabeçalho V e E.

### 2. Implementação
Toda a lógica de algoritmos manuais deve ser escrita na classe:
👉 `src/app/FacebookGraph.java`

Consulte os cards em `handbook/backlog/` para saber o que implementar (Ex: JIRA-001, JIRA-002...).

### 3. Validação (Testes)
Para verificar se sua implementação está correta e atende aos requisitos do professor, rode:
```bash
make test-all
```
**O objetivo do grupo é deixar todos os testes em VERDE (PASSED).**

### 4. Entrega Final (Notebooks)
Conforme as novas diretrizes, a entrega final deve ser feita via **Notebooks Jupyter (.ipynb)** no repositório, contendo:
- Metodologia de construção do grafo.
- Visualização da Distribuição de Graus em escala **Log-Log**.
- Ajuste por **Lei de Potência** (Cálculo do Gamma).
- Conclusão sobre a hipótese de **Escala Livre**.

---

## 🛠️ Comandos Principais do Makefile

| Comando | Descrição |
| :--- | :--- |
| `make setup` | Compila a biblioteca base `algs4`. |
| `make classes` | Compila todo o código da pasta `src/`. |
| `make generate` | Executa o conversor de dataset para o formato `algs4`. |
| `make test-all` | **(Importante)** Executa todos os testes unitários do projeto. |
| `make dev` | Executa a classe `app.Main` para ver o relatório final. |
| `make clean` | Limpa os arquivos de compilação. |

---

## 📋 Responsabilidades Mapeadas

Consulte o arquivo `handbook/PROJECT_STATUS.md` para ver quem é o responsável por cada método no `FacebookGraph.java`.

-   **Estatísticas e Graus:** Artur
-   **Conectividade e Bipartição:** Gabriel
-   **Representações e Binários:** Implementado (Revisar se necessário)

---

## 🐳 Docker (Opcional)

Se preferir rodar em um ambiente isolado:
```bash
docker build -t tg01_14-java .
docker run --rm -it -v "$PWD":/app -w /app tg01_14-java make test-all
```

---

## 📚 Referências
- **SNAP Dataset:** [https://snap.stanford.edu/data/](https://snap.stanford.edu/data/)
- **algs4 Library:** [https://github.com/kevin-wayne/algs4](https://github.com/kevin-wayne/algs4)
- **Documentação Local:** Veja os guias em `handbook/docs/`.
