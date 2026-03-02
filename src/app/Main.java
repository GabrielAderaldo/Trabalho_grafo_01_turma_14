package app;

import edu.princeton.cs.algs4.In;
import utils.NotebookDataGenerator;

/**
 * Ponto de entrada principal para a análise do dataset real do Facebook.
 * Carrega o grafo unificado e gera os artefatos para o time de Data Science.
 *
 * @author Gabriel Aderaldo
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

            // 2. Gerar todos os dados para o Notebook
            System.out.println(">>> [INFO] Gerando artefatos para o Time de Data Science...");
            NotebookDataGenerator.generateAll(fb, outputPrefix);
            
            long finished = System.currentTimeMillis();
            System.out.printf(">>> [SUCCESS] Analise concluida em %d ms!\n", (finished - loaded));
            System.out.println(">>> Verifique as pastas: data/generated/sheets/ e data/generated/plain_text/");

        } catch (Exception e) {
            System.err.println(">>> [ERROR] Falha ao processar o dataset real: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
