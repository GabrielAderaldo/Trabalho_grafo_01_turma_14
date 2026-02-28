# JIRA-003: Verificação de Bipartição (Manual)

## Descrição
Implementar o algoritmo de 2-coloração para determinar se o grafo é bipartido (não contém ciclos de comprimento ímpar).

## Requisitos
- Utilizar um array `boolean[] color` para armazenar as duas cores (true/false).
- Utilizar DFS para propagar as cores.
- Se um vizinho já estiver marcado e tiver a mesma cor que o vértice atual, o grafo NÃO é bipartido.

## 🚨 Alerta de Correção (Gabriel)
O método `checkBipartite` precisa obrigatoriamente de `marked[v] = true;` na primeira linha do corpo da função. Sem isso, o DFS entrará em loop infinito em grafos reais.

## Definição de Pronto (DoD)
- Passar no teste `tests.BipartiteTest` sem travar o computador.
