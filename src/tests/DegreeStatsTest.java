package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;

/**
 * DoD para JIRA-001: Estatísticas de Graus.
 */
public class DegreeStatsTest {
    public static void main(String[] args) {
        Graph G = new Graph(4);
        G.addEdge(0, 1); G.addEdge(1, 2); G.addEdge(2, 0); G.addEdge(2, 3);
        
        FacebookGraph fb = new FacebookGraph(G);

        // Testando os métodos que você vai implementar no Wrapper
        int max = fb.maxDegree();
        int min = fb.minDegree();
        double avg = fb.avgDegree();
        double dens = fb.density();

        boolean ok = (max == 3 && min == 1 && avg == 2.0 && dens > 0);

        if (ok) {
            System.out.println(">>> JIRA-001 PASSED!");
        } else {
            System.err.println(">>> JIRA-001 FAILED: Verifique os calculos em FacebookGraph.");
            System.exit(1);
        }
    }
}
