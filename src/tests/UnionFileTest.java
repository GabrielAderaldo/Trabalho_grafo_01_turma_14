package tests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import utils.EdgeListConverter;

/**
 * Teste de integracao para o arquivo real de uniao do Facebook.
 */
public class UnionFileTest {

    private static final Path INPUT_PATH = Paths.get("data/union_data_facebook/facebook_union.txt");
    private static final Path OUTPUT_PATH = Paths.get("data/generated/facebook_union.txt");

    public static void main(String[] args) {
        System.out.println("Iniciando teste de conversao do arquivo de uniao...");

        if (!Files.exists(INPUT_PATH)) {
            fail("Arquivo de entrada nao encontrado em: " + INPUT_PATH);
        }

        GraphStats expected;
        try {
            expected = computeStats(INPUT_PATH);
        } catch (IOException e) {
            fail("Falha ao ler estatisticas da entrada: " + e.getMessage());
            return;
        }

        EdgeListConverter.main(new String[0]);

        if (!Files.exists(OUTPUT_PATH)) {
            fail("Arquivo de saida nao foi gerado em: " + OUTPUT_PATH);
        }

        try {
            List<String> lines = Files.readAllLines(OUTPUT_PATH, StandardCharsets.UTF_8);
            if (lines.size() < 2) {
                fail("Saida invalida: menos de 2 linhas.");
            }

            int actualV = Integer.parseInt(lines.get(0).trim());
            int actualE = Integer.parseInt(lines.get(1).trim());

            if (actualV != expected.vertices) {
                fail("V inconsistente. Esperado " + expected.vertices + ", obtido " + actualV);
            }
            if (actualE != expected.edges) {
                fail("E inconsistente. Esperado " + expected.edges + ", obtido " + actualE);
            }
            if (lines.size() != actualE + 2) {
                fail("Numero de linhas inconsistente. Esperado " + (actualE + 2) + ", obtido " + lines.size());
            }
        } catch (IOException e) {
            fail("Falha ao ler arquivo de saida: " + e.getMessage());
        } catch (NumberFormatException e) {
            fail("Cabecalho de saida nao numerico: " + e.getMessage());
        }

        System.out.println(">>> TESTE PASSOU: arquivo gerado com cabecalho consistente.");
    }

    private static GraphStats computeStats(Path path) throws IOException {
        int maxVertexId = -1;
        int edges = 0;

        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                edges++;
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    try {
                        int v = Integer.parseInt(parts[0]);
                        int w = Integer.parseInt(parts[1]);
                        maxVertexId = Math.max(maxVertexId, Math.max(v, w));
                    } catch (NumberFormatException e) {
                        // Mantem a mesma regra do conversor para linhas nao numericas.
                    }
                }
            }
        }

        return new GraphStats(maxVertexId + 1, edges);
    }

    private static void fail(String message) {
        System.err.println(">>> TESTE FALHOU: " + message);
        System.exit(1);
    }

    private static final class GraphStats {
        private final int vertices;
        private final int edges;

        private GraphStats(int vertices, int edges) {
            this.vertices = vertices;
            this.edges = edges;
        }
    }
}
