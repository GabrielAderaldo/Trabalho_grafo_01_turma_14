package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import java.io.*;
import java.nio.ByteBuffer;

public class CSRTest {
    public static void main(String[] args) throws IOException {
        String path = "data/generated/test.csr";
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        
        FacebookGraph fb = new FacebookGraph(G);
        fb.exportCSR(path, false); // Grafo do Facebook não é dirigido

        File file = new File(path);
        if (!file.exists()) {
            System.err.println(">>> JIRA-008 FAILED: Arquivo nao gerado.");
            System.exit(1);
        }

        // Validação da estrutura binária básica
        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            int V = in.readInt();
            int E = in.readInt();
            
            if (V == 3 && E == 2) {
                System.out.println(">>> JIRA-008 PASSED: Metadados corretos.");
            } else {
                System.err.println(">>> JIRA-008 FAILED: V ou E incorretos no binario.");
                System.exit(1);
            }
        }
    }
}
