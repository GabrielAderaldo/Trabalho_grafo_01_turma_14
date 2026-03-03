package tests;

import app.FacebookGraph;
import edu.princeton.cs.algs4.Graph;
import utils.DataExporter;
import utils.NotebookDataGenerator;
import java.io.File;
import java.util.*;

/**
 * Teste Unificado de Exportação de Dados.
 */
public class DataExportTest {

    public static void main(String[] args) {
        System.out.println(">>> Iniciando Testes de Exportação (JSON, CSV, Binário)...");
        
        Graph G = new Graph(4);
        G.addEdge(0, 1); G.addEdge(1, 2); G.addEdge(2, 3); G.addEdge(3, 0);
        FacebookGraph fb = new FacebookGraph(G);
        
        String prefix = "export_test";
        boolean success = true;

        try {
            DataExporter.setTestMode(true);

            // 1. JSON
            Map<String, Object> metrics = new HashMap<>();
            metrics.put("v", fb.V());
            DataExporter.toJSON(prefix + "_metrics", metrics);
            if (!checkFileExists("data/generated/test/plain_text/" + prefix + "_metrics.json")) success = false;

            // 2. CSV
            int[] degrees = {2, 2, 2, 2};
            DataExporter.exportRawVertexDegrees(prefix, degrees);
            if (!checkFileExists("data/generated/test/sheets/" + prefix + "_vertex_degrees.csv")) success = false;

            // 3. Binário
            DataExporter.toBitset(prefix, fb.V(), fb.toAdjacencyBitSet());
            if (!checkFileExists("data/generated/test/bin/" + prefix + "_adjmatrix.bin")) success = false;

            // 4. Notebook
            NotebookDataGenerator.generateAll(fb, prefix + "_notebook");
            if (!checkFileExists("data/generated/test/sheets/" + prefix + "_notebook_degree_frequencies.csv")) success = false;

            if (success) {
                System.out.println("\n>>> [OK] DataExportTest PASSOU!");
            } else {
                System.err.println("\n>>> [ERRO] Alguns arquivos não foram gerados corretamente.");
                System.exit(1);
            }

        } catch (Exception e) {
            System.err.println("\n>>> [ERRO] DataExportTest FALHOU: " + e.getMessage());
            System.exit(1);
        }
    }

    private static boolean checkFileExists(String path) {
        File file = new File(path);
        if (file.exists() && file.length() > 0) {
            System.out.println("    [OK] Arquivo gerado: " + path);
            return true;
        }
        System.err.println("    [FALHA] Arquivo ausente: " + path);
        return false;
    }
}
