# 🚀 Graph Data Engine - TG01_14

Este repositório é um **Motor de Processamento de Grafos** de alta performance. O objetivo principal é transformar datasets brutos (SNAP/Facebook) em artefatos binários otimizados e relatórios estatísticos para consumo imediato em notebooks de Ciência de Dados (Jupyter/Google Colab).

---

## ⚡ Fluxo de Operação (3 Passos)

Para gerar todos os dados para análise, siga esta sequência no terminal:

1.  **Setup:** Configura a biblioteca `algs4` e o ambiente Python.
    ```bash
    make setup
    ```
2.  **Generate:** Converte o dataset bruto do Facebook para o padrão do motor.
    ```bash
    make generate
    ```
3.  **Run Engine:** Executa o motor que processa o grafo e gera os binários.
    ```bash
    make run-full
    ```

---

## 🏗️ O que o Motor Gera?

Após rodar o motor, verifique a pasta `data/generated/`:
- **`bin/`**: Grafos em **CSR (Compressed Sparse Row)** e **BitSets** (Leitura ultra-rápida em Python).
- **`sheets/`**: CSVs com distribuição de graus e frequências.
- **`plain_text/`**: JSONs com métricas globais (V, E, Densidade, Topologia).

---

## 🧪 Suíte de Testes (TDD)

O motor é validado por uma suite de testes unificada que garante a integridade dos cálculos manuais:
```bash
make test-all
```

---

## 📖 Documentação (Handbook)

Para detalhes técnicos e guias de integração, consulte a pasta [`handbook/`](./handbook/):
- [**Guia do Colab**](./handbook/COLAB_GUIDE.md): Como ler os binários usando NumPy/Python.
- [**Status do Projeto**](./handbook/PROJECT_STATUS.md): Roadmap e responsabilidades.
- [**Referência de Algoritmos**](./handbook/docs/MANUAL_GRAPH_ALGORITHMS.md): Lógica por trás das implementações manuais.

---

## 🤖 Notas para Desenvolvedores
- **Manual Calcs Only:** Este motor prioriza implementações manuais (sem usar `G.degree()` ou classes prontas de busca da `algs4`) por requisitos pedagógicos.
- **Endianness:** Todos os binários são gerados em **Big-Endian**. No Python, utilize `dtype='>i4'`.
