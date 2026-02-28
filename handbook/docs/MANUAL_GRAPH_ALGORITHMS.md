# Guia Teórico: Algoritmos de Busca e Classificação

Para implementar os requisitos, utilizem como base o livro "Algorithms, 4th Edition" (Sedgewick & Wayne). Link: [algs4.cs.princeton.edu/40graphs/](https://algs4.cs.princeton.edu/40graphs/)

## 1. Estatísticas de Grau (Contagem Manual)
Para cada vértice `v`, você deve obter o iterador de adjacência e percorrê-lo:
```text
integer degree_count = 0
for each neighbor w in adjacency_of(v):
    degree_count = degree_count + 1
return degree_count
```

## 2. Busca em Profundidade (DFS)
Base para a Conectividade (JIRA-002).
```text
dfs(G, v):
    marked[v] = true
    for each w in adj(v):
        if (!marked[w]):
            dfs(G, w)
```

## 3. Duas-Coloração (Bipartição)
Base para a JIRA-003. O algoritmo tenta atribuir cores alternadas. Se encontrar vizinhos com a mesma cor, o grafo contém um ciclo ímpar.
```text
dfs_bipartite(G, v, color):
    marked[v] = true
    for each w in adj(v):
        if (!marked[w]):
            color[w] = !color[v]
            dfs_bipartite(G, w, color)
        else if (color[w] == color[v]):
            isBipartite = false
```
