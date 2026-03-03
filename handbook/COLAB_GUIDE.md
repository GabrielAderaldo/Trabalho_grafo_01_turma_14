# 📊 Guia de Integração: Java Engine ➡️ Google Colab

Este guia explica como utilizar o motor de processamento Java para gerar artefatos de alta performance e consumi-los em um ambiente Python (Google Colab/Jupyter).

---

## 1. Preparação no Colab

Abra um novo notebook no Google Colab e, em uma célula de código, execute os comandos abaixo para clonar o projeto e preparar o ambiente:

```bash
# 1. Clonar o repositório
!git clone <URL_DO_SEU_REPOSITORIO> project
%cd project

# 2. Configurar a biblioteca algs4
!make setup

# 3. Gerar o dataset unificado
!make generate
```

---

## 2. Execução do Motor de Dados

Para processar o grafo real e gerar todos os binários, execute:

```bash
!make run-full
```

Após a execução, os arquivos estarão disponíveis na pasta `data/generated/`.

---

## 3. Consumindo os Artefatos em Python

Abaixo estão exemplos de como ler os formatos gerados usando **Python**, **NumPy** e **Struct**.

### A. Carregando Métricas Globais (JSON)
```python
import json

with open('data/generated/plain_text/facebook_union_metrics.json', 'r') as f:
    metrics = json.load(f)

print(f"Ordem (V): {metrics['v_order']}")
print(f"Densidade: {metrics['density']}")
```

### B. Carregando o Grafo em CSR (Binário - Ultra Rápido)
O formato CSR é ideal para processamento de matrizes esparsas.
```python
import numpy as np

def load_csr(path):
    with open(path, 'rb') as f:
        v = np.fromfile(f, dtype='>i4', count=1)[0] # Big-endian int32
        e = np.fromfile(f, dtype='>i4', count=1)[0]
        offsets = np.fromfile(f, dtype='>i4', count=v+1)
        edges = np.fromfile(f, dtype='>i4', count=2*e)
    return v, e, offsets, edges

v, e, offsets, edges = load_csr('data/generated/bin/facebook_union_csr.bin')
```

### C. Carregando a Lista de Arestas (Binário)
```python
def load_edge_list(path):
    with open(path, 'rb') as f:
        v = np.fromfile(f, dtype='>i4', count=1)[0]
        e = np.fromfile(f, dtype='>i4', count=1)[0]
        # Carrega pares de inteiros (u, v)
        pairs = np.fromfile(f, dtype='>i4').reshape(-1, 2)
    return v, e, pairs

v, e, edges = load_edge_list('data/generated/bin/facebook_union_edgelist.bin')
```

### D. Analisando Distribuição de Graus (CSV)
```python
import pandas as pd

df_degrees = pd.read_csv('data/generated/sheets/facebook_union_vertex_degrees.csv')
print(df_degrees.head())
```

---

## 📂 Estrutura de Arquivos Gerados

| Formato | Caminho | Descrição |
| :--- | :--- | :--- |
| **BIN** | `bin/facebook_union_csr.bin` | Grafo em Compressed Sparse Row (CSR). Melhor para performance. |
| **BIN** | `bin/facebook_union_adjmatrix.bin` | Matriz de Adjacência comprimida em bits. |
| **BIN** | `bin/facebook_union_edgelist.bin` | Lista de arestas bruta em formato binário. |
| **CSV** | `sheets/facebook_union_vertex_degrees.csv` | Tabela `Vértice,Grau` para análise estatística. |
| **JSON** | `plain_text/facebook_union_metrics.json` | Métricas globais (V, E, densidade, etc). |
| **JSON** | `plain_text/facebook_union_topology.json` | Dados de conectividade e bipartição. |

---

## 🛠 Troubleshooting

- **Erro: `javac` not found:** O Colab já vem com JDK instalado. Se estiver em outro ambiente Linux, use `sudo apt install default-jdk`.
- **Arquivos não encontrados:** Certifique-se de que rodou `make generate` antes do `make run-full`.
- **Endianness:** O Java exporta em **Big-Endian** (`>i4` no NumPy). Se os números parecerem gigantescos ou errados, verifique o prefixo `>` no `dtype`.
