package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;

public class EdgeListTest {
    public static void main(String[] args) {
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        
        FacebookGraph fb = new FacebookGraph(G);
        int[][] edges = fb.toEdgeList();

        if (edges != null && edges.length == 2) {
            // Verifica se a primeira aresta contem 0 e 1 em qualquer ordem
            boolean e0 = (edges[0][0] == 0 && edges[0][1] == 1) || (edges[0][0] == 1 && edges[0][1] == 0);
            if (e0) {
                System.out.println(">>> JIRA-011 PASSED!");
            } else {
                System.err.println(">>> JIRA-011 FAILED: Conteudo da aresta incorreto.");
                System.exit(1);
            }
        } else {
            System.err.println(">>> JIRA-011 FAILED: Tamanho do array incorreto.");
            System.exit(1);
        }
    }
}
