# Requisitos do Trabalho - Unidade I

Este documento detalha os requisitos pedagógicos da Unidade I e mapeia como eles são atendidos pelas tarefas técnicas do projeto.

## 📋 Escopo Mínimo e Evidências

Conforme as diretrizes da disciplina, o projeto deve entregar as seguintes evidências estatísticas e estruturais:

| Requisito Pedagógico | Implementação Técnica | Tarefa Relacionada |
| :--- | :--- | :--- |
| **Número de Vértices (Ordem)** | `G.V()` no Wrapper `FacebookGraph` | `JIRA-001` |
| **Número de Arestas (Tamanho)** | `G.E()` no Wrapper `FacebookGraph` | `JIRA-001` |
| **Cálculo de Densidade** | Método `density()` (Manual) | `JIRA-001` |
| **Grau Médio / Máximo / Mínimo** | Métodos `degree` (Manual) | `JIRA-001` |
| **Distribuição de Graus** | Lógica de Frequência no `Main.java` | `TASK-002` |
| **Conectividade** | DFS Manual para Componentes Conexos | `JIRA-002` |
| **Bipartição** | Algoritmo de 2-Coloração Manual | `JIRA-003` |
| **Vizinhança** | Iteração sobre Listas de Adjacência | `JIRA-004` |

## 💻 Representações Computacionais

O trabalho exige a demonstração de diferentes formas de representar o grafo na memória. Implementamos as 4 principais:

1.  **Lista de Adjacência:** Representação nativa via `algs4.Graph` e exportação via **CSR (Compressed Sparse Row)** para alta performance. (`JIRA-008`)
2.  **Matriz de Adjacência:** Implementação em array `boolean[][]` e exportação compacta via **Bitset**. (`JIRA-009`)
3.  **Matriz de Incidência:** Representação Vértice-Aresta com exportação em **Binário Esparso**. (`JIRA-010`)
4.  **Lista de Arestas:** Representação bruta em `int[][]` e exportação em **Binário de Largura Fixa**. (`JIRA-012`)

## 🎯 Mapa Didático-Metodológico

-   **Abordagem:** TDD (Test-Driven Development). Cada algoritmo manual possui um teste unitário em `src/tests/` para garantir a correção lógica sem depender de bibliotecas prontas para os cálculos finais.
-   **Dataset:** Dados reais do Facebook (SNAP), processados pelo utilitário `EdgeListConverter` para compatibilidade com a biblioteca `algs4`.
-   **Eficiência:** Foco em economia de memória (Uso de primitivos `boolean` e formatos binários compactos).

## 🚫 Delimitações (O que NÃO fazer nesta fase)
-   Não antecipar algoritmos de busca de caminho (Dijkstra, A*).
-   Não focar em fluxos de rede.
-   Focar exclusivamente em **Modelagem, Representação e Estatística Descritiva**.
