# JIRA-005: Matriz de Adjacência

## Descrição
Implementar a conversão do grafo para uma Matriz de Adjacência (array bidimensional).

## Requisitos
- Criar um array `boolean[V][V]`.
- Percorrer todas as arestas do grafo e marcar `matrix[v][w] = true` e `matrix[w][v] = true`.
- Deve ser eficiente: $O(V + E)$.

## Definição de Pronto (DoD)
- O código deve passar no teste `src/tests/AdjacencyMatrixTest.java`.
