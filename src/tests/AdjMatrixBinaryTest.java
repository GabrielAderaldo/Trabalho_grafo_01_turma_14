package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import utils.DataExporter;
import java.io.*;
import java.util.BitSet;

public class AdjMatrixBinaryTest {
    public static void main(String[] args) throws IOException {
        String name = "test_matrix";
        // O DataExporter agora define o caminho internamente: data/generated/bin/name_adjmatrix.bin
        String expectedPath = "data/generated/bin/test_matrix_adjmatrix.bin";
        
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        
        FacebookGraph fb = new FacebookGraph(G);
        BitSet bits = fb.toAdjacencyBitSet();
        DataExporter.toBitset(name, fb.V(), bits);

        File file = new File(expectedPath);
        if (file.exists() && file.length() > 0) {
            try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
                int V = in.readInt();
                if (V == 3) {
                    System.out.println(">>> JIRA-009 PASSED!");
                } else {
                    System.err.println(">>> JIRA-009 FAILED: V incorreto.");
                    System.exit(1);
                }
            }
        } else {
            System.err.println(">>> JIRA-009 FAILED: Arquivo nao encontrado em " + expectedPath);
            System.exit(1);
        }
    }
}
