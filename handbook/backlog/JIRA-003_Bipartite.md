# JIRA-003: Verificação de Bipartição (Manual)

## Descrição
Implementar o algoritmo de 2-coloração para determinar se o grafo é bipartido (não contém ciclos de comprimento ímpar).

## Requisitos
- Utilizar um array `boolean[] color` para armazenar as duas cores (true/false).
- Utilizar DFS para propagar as cores.
- Se um vizinho já estiver marcado e tiver a mesma cor que o vértice atual, o grafo NÃO é bipartido.

## Definição de Pronto (DoD)
- O código deve passar no teste `src/tests/BipartiteTest.java`.
- O teste valida um Quadrado (Bipartido) e um Triângulo (Não Bipartido).
