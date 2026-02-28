package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import utils.DataExporter;
import java.io.*;

public class EdgeListBinaryTest {
    public static void main(String[] args) throws IOException {
        String path = "data/generated/test_edges.bin";
        Graph G = new Graph(2);
        G.addEdge(0, 1);
        
        FacebookGraph fb = new FacebookGraph(G);
        // Usa o método genérico de exportação binária
        DataExporter.toBinaryEdgeList("test", fb.V(), fb.E(), fb.toEdgeList());

        File f = new File("data/generated/bin/test_edgelist.bin");
        if (f.exists()) {
            System.out.println(">>> JIRA-012 PASSED!");
        } else {
            System.err.println(">>> JIRA-012 FAILED!");
            System.exit(1);
        }
    }
}
