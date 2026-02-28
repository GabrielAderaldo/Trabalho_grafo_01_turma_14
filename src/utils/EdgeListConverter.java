package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Conversor específico para o dataset facebook_union.txt.
 * Implementa leitura de arquivo local e feedback visual de progresso.
 */
final public class EdgeListConverter {

    private static final String INPUT_PATH = "data/union_data_facebook/facebook_union.txt";
    private static final String OUTPUT_PATH = "data/generated/facebook_union.txt";

    public static void main(String[] args) {
        ArrayList<String> edges = new ArrayList<>();
        int maxVertexId = -1;
        int count = 0;
        String animation = "|/-";

        System.out.println("Lendo arquivo: " + INPUT_PATH);

        try (Scanner scanner = new Scanner(new File(INPUT_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                edges.add(line);
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    try {
                        int v = Integer.parseInt(parts[0]);
                        int w = Integer.parseInt(parts[1]);
                        maxVertexId = Math.max(maxVertexId, Math.max(v, w));
                    } catch (NumberFormatException e) {
                        // Ignora linhas mal formatadas sem interromper o processamento.
                    }
                }

                count++;
                if (count % 10000 == 0) {
                    char frame = animation.charAt((count / 10000) % animation.length());
                    System.err.print("\rProcessando: " + frame + " " + count + " arestas lidas...");
                }
            }

            System.err.println("\rConcluido: " + count + " arestas processadas.          ");
        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo nao encontrado em " + INPUT_PATH);
            return;
        }

        int V = maxVertexId + 1;
        int E = edges.size();

        File outputDir = new File("data/generated");
        outputDir.mkdirs();

        try (PrintWriter out = new PrintWriter(new FileWriter(OUTPUT_PATH))) {
            out.println(V);
            out.println(E);
            for (String edge : edges) {
                out.println(edge);
            }
            System.out.println("Sucesso! Grafo gerado em: " + OUTPUT_PATH);
            System.out.println("Estatisticas: V=" + V + ", E=" + E);
        } catch (IOException e) {
            System.err.println("Erro ao gravar arquivo: " + e.getMessage());
        }
    }
}
