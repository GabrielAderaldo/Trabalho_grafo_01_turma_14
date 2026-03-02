package app;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import utils.DataExporter;
import utils.GraphMetrics;
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

        // 3. Exportando graus brutos para análise de power law no Python
        int[] degrees = fbGraph.degrees();
        DataExporter.exportRawVertexDegrees("facebook", degrees);

        // 4. Exportando métricas agregadas em JSON (para notebooks / Python)
        GraphMetrics metrics = new GraphMetrics(
            fbGraph.V(),
            fbGraph.E(),
            fbGraph.avgDegree(),
            fbGraph.maxDegree(),
            fbGraph.minDegree(),
            fbGraph.density()
        );
        metrics.exportAsJson("facebook_metrics");

        StdOut.println(">>> Main executada com sucesso. Arquivos gerados em data/generated/");
    }
}
