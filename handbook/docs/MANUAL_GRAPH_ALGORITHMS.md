# Guia de Implementação Manual: Algoritmos de Grafos

Este guia explica como implementar as métricas do seu trabalho sem usar as classes prontas (`CC`, `Bipartite`) da `algs4`.

## 1. Estatísticas de Grau (Manual)
Em vez de `G.degree(v)`, você pode contar os elementos no iterador:

```java
int grau = 0;
for (int w : G.adj(v)) {
    grau++;
}
```

## 2. Conectividade (DFS Manual)
Para saber se o grafo é conexo e contar os componentes:

```java
// No seu Main.java ou em uma classe auxiliar:
boolean[] marked = new boolean[G.V()];
int componentes = 0;

for (int v = 0; v < G.V(); v++) {
    if (!marked[v]) {
        componentes++;
        dfs(G, v, marked);
    }
}

// Método DFS recursivo:
private static void dfs(Graph G, int v, boolean[] marked) {
    marked[v] = true;
    for (int w : G.adj(v)) {
        if (!marked[w]) {
            dfs(G, w, marked);
        }
    }
}
```

## 3. Bipartição (2-Coloração Manual)
Para verificar se o grafo é bipartido:

```java
boolean[] marked = new boolean[G.V()];
boolean[] color = new boolean[G.V()];
boolean isBipartite = true;

for (int v = 0; v < G.V(); v++) {
    if (!marked[v]) {
        if (!checkBipartite(G, v, marked, color)) {
            isBipartite = false;
            break;
        }
    }
}

// Método de verificação (DFS com cores):
private static boolean checkBipartite(Graph G, int v, boolean[] marked, boolean[] color) {
    marked[v] = true;
    for (int w : G.adj(v)) {
        if (!marked[w]) {
            color[w] = !color[v]; // Atribui a cor oposta
            if (!checkBipartite(G, w, marked, color)) return false;
        } else if (color[w] == color[v]) {
            return false; // Vizinhos com a mesma cor!
        }
    }
    return true;
}
```

## 4. Densidade
A fórmula manual que você deve usar no `Main.java`:
```java
double v = G.V();
double e = G.E();
double densidade = (2.0 * e) / (v * (v - 1));
```

## 5. Vizinhança
Para listar a vizinhança de um vértice `v`, basta iterar sobre `G.adj(v)` e imprimir os valores.
