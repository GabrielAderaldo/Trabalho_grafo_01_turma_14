package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import utils.NotebookDataGenerator;
import java.io.File;
import java.util.Scanner;

/**
 * TDD para a classe NotebookDataGenerator.
 * Esta classe deve garantir que todos os dados necessários para o Jupyter Notebook
 * sejam exportados corretamente em formatos CSV e JSON.
 */
public class NotebookDataGeneratorTest {
    public static void main(String[] args) {
        // 1. Setup de um grafo controlado (Triângulo + 1 vértice pendente)
        Graph G = new Graph(4);
        G.addEdge(0, 1); G.addEdge(1, 2); G.addEdge(2, 0); G.addEdge(2, 3);
        // Graus: 0=2, 1=2, 2=3, 3=1
        
        FacebookGraph fb = new FacebookGraph(G);
        String prefix = "test_notebook";
        
        System.out.println(">>> Iniciando Testes de Geração de Dados para Notebook...");

        try {
            // Ativa o modo de teste para salvar em data/generated/test/
            utils.DataExporter.setTestMode(true);

            // Chamada do método que centraliza todas as exportações
            NotebookDataGenerator.generateAll(fb, prefix);

            // 2. Validação: CSV de Graus Brutos
            checkFileExists("data/generated/test/sheets/" + prefix + "_vertex_degrees.csv");
            checkCSVContent("data/generated/test/sheets/" + prefix + "_vertex_degrees.csv", "Vertice,Grau", 4);

            // 3. Validação: CSV de Frequência de Graus
            checkFileExists("data/generated/test/sheets/" + prefix + "_degree_frequencies.csv");
            checkCSVContent("data/generated/test/sheets/" + prefix + "_degree_frequencies.csv", "Grau,Quantidade", 3); // Graus 1, 2 e 3

            // 4. Validação: JSON de Métricas
            checkFileExists("data/generated/test/plain_text/" + prefix + "_metrics.json");
            
            System.out.println(">>> NotebookDataGeneratorTest PASSED!");

        } catch (Exception e) {
            System.err.println(">>> NotebookDataGeneratorTest FAILED: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void checkFileExists(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new Exception("Arquivo não encontrado: " + path);
        }
        System.out.println("  [OK] Arquivo gerado: " + path);
    }

    private static void checkCSVContent(String path, String expectedHeader, int minLines) throws Exception {
        Scanner sc = new Scanner(new File(path));
        if (!sc.hasNextLine()) throw new Exception("CSV Vazio: " + path);
        
        String header = sc.nextLine();
        if (!header.equals(expectedHeader)) {
            throw new Exception("Header incorreto no CSV " + path + ". Esperado: " + expectedHeader);
        }

        int count = 0;
        while (sc.hasNextLine()) {
            sc.nextLine();
            count++;
        }
        if (count < minLines) {
            throw new Exception("O CSV " + path + " deveria ter pelo menos " + minLines + " linhas de dados.");
        }
        sc.close();
    }
}
