package app;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import utils.DataExporter;
import app.types.CRSData;

/**
 * Classe principal que demonstra o uso da infraestrutura de exportação.
 */
public class Main {
    public static void main(String[] args) {
        String inputPath = "data/generated/facebook_union.txt";
        File file = new File(inputPath);

        if (!file.exists()) { 
            StdOut.println("ERRO: Dataset não encontrado em " + inputPath);
            return; 
        }

        In in = new In(inputPath);
        final FacebookGraph fbGraph = FacebookGraph.fromIn(in);

        // Garantir diretórios
        new File("data/generated/bin").mkdirs();
        new File("data/generated/sheets").mkdirs();
        new File("data/generated/plain_text").mkdirs();

        // 1. Exportando CSR (Uso correto dos métodos do Record/Type)
        CRSData csr = fbGraph.toCRS(false);
        DataExporter.exportCSR("facebook", fbGraph.V(), fbGraph.E(), csr.offsets(), csr.edges());

        // 2. Exportando Bitset
        DataExporter.toBitset("facebook", fbGraph.V(), fbGraph.toAdjacencyBitSet());

        // 3. Exportando Metadados JSON
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("v", fbGraph.V());
        metrics.put("e", fbGraph.E());
        DataExporter.toJSON("facebook_metrics", metrics);

        StdOut.println(">>> Main executada com sucesso. Arquivos gerados em data/generated/");
    }
}
