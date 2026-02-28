# Referência de Relatório: Classificação do Grafo

Copie o código abaixo para o seu `src/app/Main.java` para gerar os dados do relatório.

## Código de Análise (`src/app/Main.java`)

```java
package app;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.CC;
import edu.princeton.cs.algs4.Bipartite;

public class Main {
    public static void main(String[] args) {
        String path = "data/generated/facebook_union.txt";
        In in = new In(path);
        Graph G = new Graph(in);

        StdOut.println("================================================");
        StdOut.println("   RELATÓRIO DE CLASSIFICAÇÃO DO GRAFO");
        StdOut.println("================================================");

        // 1. Representação Computacional
        StdOut.println("
1. REPRESENTAÇÃO COMPUTACIONAL");
        StdOut.println("- Tipo: Lista de Adjacência (algs4.Graph)");
        StdOut.println("- Justificativa: Eficiente para grafos esparsos (E << V²).");
        StdOut.println("- Custo de Memória: O(V + E)");

        // 2. Topologia
        StdOut.println("
2. TOPOLOGIA");
        StdOut.println("- Ordem (V): " + G.V());
        StdOut.println("- Tamanho (E): " + G.E());
        
        CC cc = new CC(G);
        StdOut.println("- Conexo: " + (cc.count() == 1 ? "Sim" : "Não (" + cc.count() + " componentes)"));
        
        Bipartite bip = new Bipartite(G);
        StdOut.println("- Bipartido: " + (bip.isBipartite() ? "Sim" : "Não"));

        // 3. Densidade
        double densidade = (2.0 * G.E()) / (G.V() * (G.V() - 1));
        StdOut.printf("- Densidade: %.4f (%.2f%% das conexões possíveis)
", densidade, densidade * 100);

        // 4. Graus
        int max = 0;
        int min = G.V();
        int sum = 0;
        for (int v = 0; v < G.V(); v++) {
            int d = G.degree(v);
            if (d > max) max = d;
            if (d < min) min = d;
            sum += d;
        }
        StdOut.println("
4. ESTATÍSTICAS DE GRAUS");
        StdOut.println("- Grau Máximo: " + max);
        StdOut.println("- Grau Mínimo: " + min);
        StdOut.printf("- Grau Médio: %.2f
", (double) sum / G.V());

        // 5. Vizinhança (Exemplo: Nó 0)
        StdOut.println("
5. VIZINHANÇA (Exemplo: Nó 0)");
        StdOut.print("- Vizinhos de 0: ");
        int count = 0;
        for (int w : G.adj(0)) {
            if (count < 10) StdOut.print(w + " ");
            count++;
        }
        StdOut.println("... (total: " + G.degree(0) + ")");
        StdOut.println("================================================");
    }
}
```

## Guia para o Relatório Escrito

Com base na saída do código acima, aqui está como você deve preencher o seu relatório:

### 1. Representação Computacional
O grafo foi implementado utilizando **Listas de Adjacência**. Esta escolha é ideal para o dataset do Facebook porque, apesar de ter 4.039 usuários, a densidade é baixa (~1%). Uma matriz de adjacência ocuparia ~16 milhões de entradas ($V^2$), enquanto a lista de adjacência ocupa apenas o necessário para as 88 mil arestas.

### 2. Topologia
- **Tipo:** Grafo Simples, Não-Direcionado.
- **Conectividade:** (Verifique a saída do código). Se o `cc.count() == 1`, o grafo é conexo, significando que existe um caminho entre qualquer par de usuários.
- **Bipartição:** Redes sociais raramente são bipartidas devido aos "triângulos" de amizade (se A é amigo de B e C, e B e C são amigos entre si, o grafo não é bipartido).

### 3. Densidade
A densidade de ~0.0108 indica que esta é uma **Rede Esparsa**. Em termos humanos, isso significa que cada usuário conhece apenas uma fração muito pequena de toda a rede.

### 4. Graus e Vizinhanças
- **Grau:** Representa o número de amigos de um usuário.
- **Grau Máximo:** Identifica o "hub" ou o influenciador da rede.
- **Vizinhança:** O conjunto de adjacência de um vértice. No relatório, você pode citar que a vizinhança do nó 0 representa o seu "círculo social imediato".
