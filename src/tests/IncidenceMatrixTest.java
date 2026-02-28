package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;

public class IncidenceMatrixTest {
    public static void main(String[] args) {
        Graph G = new Graph(3);
        G.addEdge(0, 1); // Aresta 0
        G.addEdge(1, 2); // Aresta 1

        FacebookGraph fb = new FacebookGraph(G);
        boolean[][] matrix = fb.toIncidenceMatrix();

        // Verifica se a primeira aresta (coluna 0) conecta 0 e 1
        boolean e0 = matrix[0][0] && matrix[1][0] && !matrix[2][0];
        // Verifica se a segunda aresta (coluna 1) conecta 1 e 2
        boolean e1 = !matrix[0][1] && matrix[1][1] && matrix[2][1];

        if (matrix != null && matrix.length == 3 && matrix[0].length == 2 && e0 && e1) {
            System.out.println(">>> JIRA-006 PASSED!");
        } else {
            System.err.println(">>> JIRA-006 FAILED!");
            System.exit(1);
        }
    }
}
