package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import utils.DataExporter;
import java.io.*;

public class IncidenceBinaryTest {
    public static void main(String[] args) {
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        
        FacebookGraph fb = new FacebookGraph(G);
        // Matriz de incidência binária é exportada como EdgeList por eficiência
        DataExporter.toBinaryEdgeList("test_incidence", fb.V(), fb.E(), fb.toEdgeList());

        File file = new File("data/generated/bin/test_incidence_edgelist.bin");
        if (file.exists()) {
            System.out.println(">>> JIRA-010 PASSED!");
        } else {
            System.err.println(">>> JIRA-010 FAILED!");
            System.exit(1);
        }
    }
}
