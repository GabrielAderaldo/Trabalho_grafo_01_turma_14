package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import java.io.*;

public class IncidenceBinaryTest {
    public static void main(String[] args) throws IOException {
        String path = "data/generated/test_incidence.bin";
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        
        FacebookGraph fb = new FacebookGraph(G);
        fb.exportIncidenceMatrixBinary(path);

        try (DataInputStream in = new DataInputStream(new FileInputStream(path))) {
            int V = in.readInt();
            int E = in.readInt();
            
            // Lê a primeira aresta
            int v1 = in.readInt();
            int w1 = in.readInt();
            
            if (V == 3 && E == 2 && v1 == 0 && w1 == 1) {
                System.out.println(">>> JIRA-010 PASSED!");
            } else {
                System.err.println(">>> JIRA-010 FAILED!");
                System.exit(1);
            }
        }
    }
}
