package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import java.io.*;

public class EdgeListBinaryTest {
    public static void main(String[] args) throws IOException {
        String path = "data/generated/test_edges.bin";
        Graph G = new Graph(2);
        G.addEdge(0, 1);
        
        FacebookGraph fb = new FacebookGraph(G);
        fb.exportEdgeListBinary(path);

        File f = new File(path);
        // Header(8) + 1 aresta(8) = 16 bytes
        if (f.length() == 16) {
            System.out.println(">>> JIRA-012 PASSED!");
        } else {
            System.err.println(">>> JIRA-012 FAILED: Tamanho do arquivo incorreto: " + f.length());
            System.exit(1);
        }
    }
}
