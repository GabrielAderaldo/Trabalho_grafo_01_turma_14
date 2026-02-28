package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;

public class AdjacencyListVisualTest {
    public static void main(String[] args) {
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        
        FacebookGraph fb = new FacebookGraph(G);
        String output = fb.toStringAdjacencyList();
        
        System.out.println("Saida obtida:\n" + output);

        // Critérios de Aceite:
        boolean hasHeader = output.contains("Vertice 0:");
        boolean hasNeighbors = output.contains("1") && output.contains("2");
        boolean hasMultipleLines = output.split("\n").length >= 3;

        if (hasHeader && hasNeighbors && hasMultipleLines) {
            System.out.println("\n>>> JIRA-007 PASSED!");
        } else {
            System.err.println("\n>>> JIRA-007 FAILED: Formato invalido.");
            System.exit(1);
        }
    }
}
