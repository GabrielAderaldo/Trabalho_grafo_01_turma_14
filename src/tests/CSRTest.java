package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import app.types.CRSData;
import utils.DataExporter;
import java.io.*;

public class CSRTest {
    public static void main(String[] args) throws IOException {
        String path = "data/generated/test.csr";
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        
        FacebookGraph fb = new FacebookGraph(G);
        CRSData data = fb.toCRS(false);
        DataExporter.exportCSR("test", fb.V(), fb.E(), data.offsets(), data.edges());

        File file = new File("data/generated/bin/test_csr.bin");
        if (file.exists()) {
            System.out.println(">>> JIRA-008 PASSED!");
        } else {
            System.err.println(">>> JIRA-008 FAILED!");
            System.exit(1);
        }
    }
}
