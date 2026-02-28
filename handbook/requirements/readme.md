# Diretrizes do Trabalho: Grafos de Escala Livre (SNAP)

Este documento detalha os requisitos da Unidade I, focados na caracterização estrutural de redes reais e investigação da hipótese de **escala livre**.

## 📋 Requisitos Técnicos Obrigatórios (Restrição Pedagógica)

### 1. Métricas de Base (Manual)
*   **Ordem $|V|$ e Tamanho $|E|$**: Extração direta da estrutura.
*   **Cálculo de Graus**: 
    - **🚨 PROIBIÇÃO:** Não é permitido o uso de `G.degree(v)`.
    - **DESAFIO:** O aluno deve implementar um iterador que percorre a lista de adjacência e conta os elementos manualmente para determinar o grau de cada nó.
*   **Densidade**: Aplicação da fórmula matemática fundamental.

### 2. Algoritmos de Estrutura (Referência: algs4.cs.princeton.edu)
As implementações de **Conectividade** e **Bipartição** devem seguir o modelo de algoritmos de busca em grafos (DFS/BFS) sem o uso das classes prontas da biblioteca.

### 3. Análise de Escala Livre (Notebook)
*   Produção de histogramas e análise de regressão para verificar a **Lei de Potência**.
*   Justificativa teórica baseada em Network Science (Barabási).
