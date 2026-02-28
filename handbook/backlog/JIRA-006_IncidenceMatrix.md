# JIRA-006: Matriz de Incidência

## Descrição
Implementar a conversão do grafo para uma Matriz de Incidência ($V 	imes E$).

## Requisitos
- Criar um array `boolean[V][E]`.
- Cada coluna representa uma aresta única.
- Se a aresta $e_i$ conecta os vértices $v$ e $w$, então `matrix[v][i] = true` e `matrix[w][i] = true`.
- **Aviso:** Esta matriz é muito grande. Garanta que o Java tenha memória suficiente (`-Xmx1G`).

## Definição de Pronto (DoD)
- O código deve passar no teste `src/tests/IncidenceMatrixTest.java`.
