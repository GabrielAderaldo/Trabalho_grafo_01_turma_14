package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import java.io.*;
import java.util.BitSet;

public class AdjMatrixBinaryTest {
    public static void main(String[] args) throws IOException {
        String path = "data/generated/test_matrix.bin";
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        
        FacebookGraph fb = new FacebookGraph(G);
        fb.exportAdjacencyMatrix(path);

        File file = new File(path);
        if (!file.exists()) {
            System.err.println(">>> JIRA-009 FAILED: Arquivo nao gerado.");
            System.exit(1);
        }

        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            int V = in.readInt();
            byte[] bytes = in.readAllBytes();
            BitSet bits = BitSet.valueOf(bytes);

            // Verifica conexões (0,1) e (1,2)
            // Posição no BitSet = v * V + w
            boolean ok = bits.get(0 * 3 + 1) && bits.get(1 * 3 + 0) &&
                         bits.get(1 * 3 + 2) && bits.get(2 * 3 + 1) &&
                         !bits.get(0 * 3 + 2);

            if (V == 3 && ok) {
                System.out.println(">>> JIRA-009 PASSED!");
            } else {
                System.err.println(">>> JIRA-009 FAILED: Dados incorretos no Bitset.");
                System.exit(1);
            }
        }
    }
}
