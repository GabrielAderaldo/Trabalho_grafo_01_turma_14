package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;

/**
 * DoD para JIRA-002: Conectividade.
 */
public class ConnectivityTest {
    public static void main(String[] args) {
        // Grafo com 2 componentes: {0,1} e {2,3}
        Graph G = new Graph(4);
        G.addEdge(0, 1);
        G.addEdge(2, 3);

        FacebookGraph fb = new FacebookGraph(G);
        int components = fb.countComponents();

        if (components == 2) {
            System.out.println(">>> JIRA-002 PASSED!");
        } else {
            System.err.println(">>> JIRA-002 FAILED: Esperado 2, obtido " + components);
            System.exit(1);
        }
    }
}
