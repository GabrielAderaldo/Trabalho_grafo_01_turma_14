# Trabalho de Grafos - Turma 14

Repositorio da disciplina para analise de grafos com o dataset do SNAP (Facebook ego networks) e a biblioteca `algs4` (Princeton).

---

## ⚡ Quick Start (Configuração em Um Clique)

Se você acabou de clonar o repositório, rode o script de bootstrap para configurar **Java, Python (venv) e o Dataset** automaticamente:

**No Mac ou Linux:**
```bash
./scripts/bootstrap.sh
```

**No Windows (PowerShell):**
```powershell
./scripts/bootstrap.ps1
```

*O que isso faz? Instala dependências do Python no `venv`, baixa submodules, compila o código Java e gera o dataset real do Facebook.*

---

## 🏗️ Estrutura do Projeto
...
├── notebooks/              # Guia de importação e Notebook de Análise (.ipynb)
├── scripts/                # Scripts de configuração (Bootstrap)
├── Taskfile.yml            # Automação moderna (Alternativa ao Makefile)
├── requirements.txt        # Dependências do Python (Data Science)
...
```

---

## 🚀 Fluxo de Trabalho (Para a Equipe)
...
### 4. Entrega Final (Notebooks)
O time de Data Science deve utilizar o ambiente virtual criado pelo bootstrap:
1. Ative o ambiente: `source venv/bin/activate` (Mac/Linux) ou `.\venv\Scripts\Activate.ps1` (Windows).
2. Abra o Jupyter: `jupyter-lab`.
3. Utilize o notebook base em `notebooks/analise_facebook.ipynb` que já possui a infra de carga pronta.

---

## 🛠️ Comandos de Automação (Make ou Task)

Você pode usar tanto o `make` quanto o `task` (recomendado para Windows):

| Comando | Descrição |
| :--- | :--- |
| `make run-full` | **(Dataset Real)** Executa a análise nos 4039 usuários e gera dados para o Notebook. |
| `make test-all` | Executa todos os 18 testes unitários do projeto. |
| `make test-notebook`| Valida apenas a geração de artefatos para o Data Science. |
| `make generate` | Prepara o arquivo `facebook_union.txt` para a `algs4`. |
...

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
