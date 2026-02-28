# JIRA-007: Visualização da Lista de Adjacência

## Descrição
Implementar um método que gera uma representação textual da Lista de Adjacência do grafo.

## Requisitos
- Implementar o método `String toStringAdjacencyList()` na classe `app.FacebookGraph`.
- O formato de saída deve ser: `Vértice: vizinho1 vizinho2 ...`
- Como o grafo do Facebook é gigante, o método deve aceitar um parâmetro para limitar quantos vértices mostrar (ex: `toStringAdjacencyList(int limit)`).

## Exemplo de Saída
```text
0: 1 2 5 10 
1: 0 2 
2: 0 1 5
```

## Definição de Pronto (DoD)
- O código deve passar no teste `src/tests/AdjacencyListVisualTest.java`.
