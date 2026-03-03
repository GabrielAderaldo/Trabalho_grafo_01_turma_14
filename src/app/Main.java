package app;

import edu.princeton.cs.algs4.In;
import utils.NotebookDataGenerator;
import utils.DataExporter;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Motor de Geração de Artefatos (High-Performance Data Engine).
 * 
 * Este é o ponto de entrada principal do projeto. Seu objetivo não é ser um guia interativo,
 * mas sim um motor que processa o grafo real e gera todos os artefatos binários e textuais
 * necessários para consumo em notebooks externos (Jupyter/Google Colab).
 * 
 * Comandos relacionados:
 * - make generate  : Prepara o arquivo de texto inicial.
 * - make run-full  : Executa este motor.
 */
public class Main {
    public static void main(String[] args) {
        String inputPath = "data/generated/facebook_union.txt";
        String outputPrefix = "facebook_union";

        System.out.println("======================================================");
        System.out.println("   MOTOR DE GERAÇÃO DE ARTEFATOS - TG01_14");
        System.out.println("======================================================");
        
        try {
            // 1. Verificação de Pré-requisitos
            File inputFile = new File(inputPath);
            if (!inputFile.exists()) {
                System.err.println(">>> [ERRO] Dataset não encontrado em: " + inputPath);
                System.err.println(">>> Por favor, execute 'make generate' primeiro.");
                System.exit(1);
            }

            // 2. Carga do Grafo
            System.out.print(">>> Carregando grafo na memória... ");
            long start = System.currentTimeMillis();
            FacebookGraph fb = FacebookGraph.fromIn(new In(inputPath));
            long loaded = System.currentTimeMillis();
            System.out.printf("CONCLUÍDO (%d ms)\n", (loaded - start));
            System.out.printf("    Estatísticas Iniciais: Ordem(V)=%d, Tamanho(E)=%d\n", fb.V(), fb.E());

            // 3. Processamento de Algoritmos Estruturais (Complexidade O(V+E))
            System.out.print(">>> Analisando topologia (Componentes e Bipartição)... ");
            int components = fb.countComponents();
            boolean isBipartite = fb.isBipartite();
            System.out.println("CONCLUÍDO");

            // 4. Exportação Global de Métricas (JSON para o Colab)
            // Estendendo o que o NotebookDataGenerator já faz
            System.out.print(">>> Gerando artefatos binários e relatórios... ");
            NotebookDataGenerator.generateAll(fb, outputPrefix);
            
            // Adicionando métricas extras que calculamos aqui ao JSON de métricas
            updateGlobalMetrics(outputPrefix, components, isBipartite);
            System.out.println("CONCLUÍDO");

            // 5. Relatório Final de Artefatos
            long finished = System.currentTimeMillis();
            printSummary(outputPrefix, (finished - start));

        } catch (Exception e) {
            System.err.println("\n>>> [CRITICAL ERROR] Falha catastrófica no motor:");
            System.err.println("    " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Atualiza o arquivo de métricas JSON com dados adicionais de algoritmos de busca.
     */
    private static void updateGlobalMetrics(String prefix, int components, boolean isBipartite) {
        // Como o NotebookDataGenerator já criou o arquivo, aqui poderíamos 
        // ou refatorar lá ou apenas adicionar as novas métricas em um mapa separado.
        // Vamos gerar um arquivo de topologia específico.
        Map<String, Object> topo = new HashMap<>();
        topo.put("connected_components", components);
        topo.put("is_bipartite", isBipartite);
        DataExporter.toJSON(prefix + "_topology", topo);
    }

    private static void printSummary(String prefix, long totalTime) {
        System.out.println("\n======================================================");
        System.out.println("   RELATÓRIO DE GERAÇÃO CONCLUÍDO");
        System.out.println("======================================================");
        System.out.printf("Tempo total de execução: %d ms\n", totalTime);
        System.out.println("Artefatos disponíveis em 'data/generated/':");
        System.out.println("  [BIN]  ./bin/" + prefix + "_csr.bin        (CSR Ultra-Fast)");
        System.out.println("  [BIN]  ./bin/" + prefix + "_adjmatrix.bin  (Adjacency BitSet)");
        System.out.println("  [BIN]  ./bin/" + prefix + "_edgelist.bin   (Binary EdgeList)");
        System.out.println("  [CSV]  ./sheets/" + prefix + "_vertex_degrees.csv");
        System.out.println("  [JSON] ./plain_text/" + prefix + "_metrics.json");
        System.out.println("  [JSON] ./plain_text/" + prefix + "_topology.json");
        System.out.println("======================================================");
        System.out.println(">>> O projeto está pronto para análise no Google Colab.");
    }
}
