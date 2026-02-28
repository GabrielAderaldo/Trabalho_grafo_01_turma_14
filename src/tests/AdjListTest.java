package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import java.util.List;

/**
 * Valida o método adjList() do Wrapper.
 */
public class AdjListTest {
    public static void main(String[] args) {
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        
        FacebookGraph fb = new FacebookGraph(G);
        Iterable<Integer>[] adj = fb.adjList();

        boolean ok = true;
        
        // Verifica se a posição 0 tem 2 vizinhos
        int count = 0;
        for (int w : adj[0]) count++;
        
        if (count != 2) {
            System.err.println("FALHA: Vertice 0 deveria ter 2 vizinhos, encontrou " + count);
            ok = false;
        }

        if (ok) {
            System.out.println(">>> TESTE DE LISTA DE ADJACENCIA PASSOU!");
        } else {
            System.exit(1);
        }
    }
}
