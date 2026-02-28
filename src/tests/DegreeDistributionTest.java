package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DegreeDistributionTest {
    public static void main(String[] args) throws IOException {
        String path = "data/generated/test_dist.csv";
        // Grafo: 0-1, 0-2 (v0 grau 2, v1 grau 1, v2 grau 1)
        Graph G = new Graph(3);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        
        FacebookGraph fb = new FacebookGraph(G);
        // O método deve ser implementado no Wrapper
        fb.exportDegreeDistribution(path);

        File f = new File(path);
        if (!f.exists()) {
            System.err.println(">>> JIRA-014 FAILED: CSV nao gerado.");
            System.exit(1);
        }

        List<String> lines = Files.readAllLines(Paths.get(path));
        // Esperado (exemplo): 
        // Grau,Frequencia
        // 1,2
        // 2,1
        
        boolean hasData = lines.size() >= 2;
        
        if (hasData) {
            System.out.println(">>> JIRA-014 PASSED!");
        } else {
            System.err.println(">>> JIRA-014 FAILED: CSV vazio.");
            System.exit(1);
        }
    }
}
