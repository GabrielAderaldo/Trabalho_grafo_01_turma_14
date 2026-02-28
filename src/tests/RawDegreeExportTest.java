package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import utils.DataExporter;
import java.io.*;

public class RawDegreeExportTest {
    public static void main(String[] args) throws IOException {
        Graph G = new Graph(10);
        G.addEdge(0, 1);
        
        FacebookGraph fb = new FacebookGraph(G);
        
        // Simulação do cálculo que o Lucas faria para alimentar o DataExporter
        int[] degrees = new int[G.V()];
        for(int v=0; v<G.V(); v++) {
            int d = 0;
            for(int w : G.adj(v)) d++;
            degrees[v] = d;
        }
        
        DataExporter.exportRawVertexDegrees("test", degrees);

        File f = new File("data/generated/sheets/test_vertex_degrees.csv");
        if (f.exists()) {
            System.out.println(">>> JIRA-014 PASSED!");
        } else {
            System.err.println(">>> JIRA-014 FAILED!");
            System.exit(1);
        }
    }
}
