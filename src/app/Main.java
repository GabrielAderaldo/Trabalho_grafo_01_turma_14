package app;

import edu.princeton.cs.algs4.In;
import utils.DataExporter;
import utils.GraphMetrics;
import utils.NotebookDataGenerator;
import app.types.CRSData;

/**
 * Ponto de entrada principal para a análise do dataset real do Facebook.
 * Carrega o grafo unificado e gera os artefatos para o time de Data Science.
 *
 * Integra:
 * - Geração dos artefatos para notebooks (NotebookDataGenerator)
 * - Exportação de representações binárias
 * - Exportação de graus e métricas agregadas para análise em Python
 */
public class Main {
    public static void main(String[] args) {
        String inputPath = "data/generated/facebook_union.txt";
        String outputPrefix = "facebook_union";

        System.out.println(">>> [INFO] Carregando Grafo do Facebook: " + inputPath);

        try {
            long start = System.currentTimeMillis();

            // 1. Carregar o Grafo (Formato algs4)
            In in = new In(inputPath);
            if (!in.exists()) {
                System.err.println("ERRO: Arquivo " + inputPath + " nao encontrado. Rode 'make generate' primeiro.");
                System.exit(1);
            }

            FacebookGraph fb = FacebookGraph.fromIn(in);

            long loaded = System.currentTimeMillis();
            System.out.printf(">>> [OK] Grafo carregado em %d ms (V=%d, E=%d)\n",
                    (loaded - start), fb.V(), fb.E());

            // 2. Gerar todos os dados para o Notebook (comportamento original da main)
            System.out.println(">>> [INFO] Gerando artefatos para o Time de Data Science...");
            NotebookDataGenerator.generateAll(fb, outputPrefix);

            long finished = System.currentTimeMillis();
            System.out.printf(">>> [SUCCESS] Analise concluida em %d ms!\n", (finished - loaded));
            System.out.println(">>> Verifique as pastas: data/generated/sheets/ e data/generated/plain_text/");

            // 3. Exportar representações adicionais para integração com Python
            // 3.1 CSR binário
            CRSData csr = fb.toCRS(false);
            DataExporter.exportCSR("facebook", fb.V(), fb.E(), csr.offsets(), csr.edges());

            // 3.2 Matriz de adjacência comprimida em BitSet
            DataExporter.toBitset("facebook", fb.V(), fb.toAdjacencyBitSet());

            // 3.3 Graus brutos para análise de power law
            int[] degrees = fb.degrees();
            DataExporter.exportRawVertexDegrees("facebook", degrees);

            // 3.4 Métricas agregadas em JSON
            GraphMetrics metrics = new GraphMetrics(
                    fb.V(),
                    fb.E(),
                    fb.avgDegree(),
                    fb.maxDegree(),
                    fb.minDegree(),
                    fb.density()
            );
            metrics.exportAsJson("facebook_metrics");

        } catch (Exception e) {
            System.err.println(">>> [ERROR] Falha ao processar o dataset real: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
