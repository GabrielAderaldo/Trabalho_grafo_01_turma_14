package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import java.util.List;

public class AdjListTest {
    public static void main(String[] args) {
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        
        FacebookGraph fb = new FacebookGraph(G);
        List<Integer>[] adj = fb.toAdjacencyList(); // Nome atualizado

        boolean ok = (adj[0].size() == 2);
        
        if (ok) {
            System.out.println(">>> TESTE DE LISTA DE ADJACENCIA PASSOU!");
        } else {
            System.err.println("FALHA: Vertice 0 deveria ter 2 vizinhos.");
            System.exit(1);
        }
    }
}
