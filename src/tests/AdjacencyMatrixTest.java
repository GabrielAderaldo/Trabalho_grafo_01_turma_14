package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;

public class AdjacencyMatrixTest {
    public static void main(String[] args) {
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(1, 2);

        FacebookGraph fb = new FacebookGraph(G);
        boolean[][] matrix = fb.toAdjacencyMatrix();

        if (matrix != null && matrix[0][1] && matrix[1][0] && matrix[1][2] && !matrix[0][2]) {
            System.out.println(">>> JIRA-005 PASSED!");
        } else {
            System.err.println(">>> JIRA-005 FAILED!");
            System.exit(1);
        }
    }
}
