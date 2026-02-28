package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;

/**
 * DoD para JIRA-003: Bipartição.
 */
public class BipartiteTest {
    public static void main(String[] args) {
        // Grafo 1: Quadrado (Bipartido)
        Graph g1 = new Graph(4);
        g1.addEdge(0, 1); g1.addEdge(1, 2); g1.addEdge(2, 3); g1.addEdge(3, 0);
        FacebookGraph fb1 = new FacebookGraph(g1);

        // Grafo 2: Triângulo (Não Bipartido)
        Graph g2 = new Graph(3);
        g2.addEdge(0, 1); g2.addEdge(1, 2); g2.addEdge(2, 0);
        FacebookGraph fb2 = new FacebookGraph(g2);

        if (fb1.isBipartite() && !fb2.isBipartite()) {
            System.out.println(">>> JIRA-003 PASSED!");
        } else {
            System.err.println(">>> JIRA-003 FAILED: Erro na detecçao de biparticao.");
            System.exit(1);
        }
    }
}
