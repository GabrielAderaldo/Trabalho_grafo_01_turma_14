# JIRA-011: Lista de Arestas (Edge List)

## Descrição
Implementar o método `toEdgeList()` que retorna o conjunto de todas as conexões únicas do grafo em um array bidimensional.

## Requisitos
- Retornar um `int[E][2]`.
- Cada linha `i` deve conter os dois vértices da aresta: `edges[i][0]` e `edges[i][1]`.
- Garantir que cada aresta apareça apenas uma vez (usando a lógica `v < w`).

## Definição de Pronto (DoD)
- O código deve passar no teste `src/tests/EdgeListTest.java`.
