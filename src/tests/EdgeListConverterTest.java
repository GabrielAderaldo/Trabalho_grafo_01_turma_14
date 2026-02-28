package tests;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import utils.EdgeListConverter;

/**
 * Testes de regressao para EdgeListConverter no fluxo de arquivo fixo
 * (INPUT_PATH -> OUTPUT_PATH).
 */
public class EdgeListConverterTest {

    private static final Path INPUT_PATH = Paths.get("data/union_data_facebook/facebook_union.txt");
    private static final Path OUTPUT_PATH = Paths.get("data/generated/facebook_union.txt");

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        runTest("testConversaoBasica", EdgeListConverterTest::testConversaoBasica);
        runTest("testArestasSaida", EdgeListConverterTest::testArestasSaida);
        runTest("testVerticeNaoSequencial", EdgeListConverterTest::testVerticeNaoSequencial);
        runTest("testLinhasVaziasSaoIgnoradas", EdgeListConverterTest::testLinhasVaziasSaoIgnoradas);
        runTest("testUmaArestaApenas", EdgeListConverterTest::testUmaArestaApenas);
        runTest("testAlgs4ParsingVE", EdgeListConverterTest::testAlgs4ParsingVE);
        runTest("testAlgs4Adjacencia", EdgeListConverterTest::testAlgs4Adjacencia);
        runTest("testArquivoRealMantemCabecalhoConsistente", EdgeListConverterTest::testArquivoRealMantemCabecalhoConsistente);

        System.out.println("\n=== Resultado: " + passed + " passou(aram), " + failed + " falhou(aram) ===");
        if (failed > 0) {
            System.out.println(">>> TESTE FALHOU!");
            System.exit(1);
        }
        System.out.println(">>> TESTE CONCLUIDO COM SUCESSO!");
    }

    @FunctionalInterface
    interface TestCase {
        boolean run() throws Exception;
    }

    private static void runTest(String name, TestCase tc) {
        try {
            if (tc.run()) {
                System.out.println("[OK] " + name);
                passed++;
            } else {
                System.out.println("[FALHOU] " + name);
                failed++;
            }
        } catch (Exception e) {
            System.err.println("[ERRO] " + name + ": " + e.getMessage());
            failed++;
        }
    }

    public static boolean testConversaoBasica() throws Exception {
        List<String> lines = runConverterWithSyntheticInput("0 1\n1 2\n2 5\n5 0\n");
        if (lines.size() < 6) {
            System.err.println("  Saida insuficiente.");
            return false;
        }

        int actualV = Integer.parseInt(lines.get(0));
        int actualE = Integer.parseInt(lines.get(1));
        if (actualV != 6) {
            System.err.println("  V esperado 6, obtido " + actualV);
            return false;
        }
        if (actualE != 4) {
            System.err.println("  E esperado 4, obtido " + actualE);
            return false;
        }
        return true;
    }

    public static boolean testArestasSaida() throws Exception {
        String input = "0 1\n1 2\n2 5\n5 0\n";
        List<String> expectedEdges = Arrays.asList("0 1", "1 2", "2 5", "5 0");

        List<String> lines = runConverterWithSyntheticInput(input);
        if (lines.size() < 2 + expectedEdges.size()) {
            System.err.println("  Linhas de saida insuficientes: " + lines.size());
            return false;
        }

        for (int i = 0; i < expectedEdges.size(); i++) {
            String got = lines.get(2 + i);
            String exp = expectedEdges.get(i);
            if (!got.equals(exp)) {
                System.err.println("  Aresta[" + i + "]: esperado '" + exp + "', obtido '" + got + "'");
                return false;
            }
        }
        return true;
    }

    public static boolean testVerticeNaoSequencial() throws Exception {
        List<String> lines = runConverterWithSyntheticInput("0 100\n1 2\n");
        int actualV = Integer.parseInt(lines.get(0));
        int actualE = Integer.parseInt(lines.get(1));
        if (actualV != 101) {
            System.err.println("  V esperado 101, obtido " + actualV);
            return false;
        }
        if (actualE != 2) {
            System.err.println("  E esperado 2, obtido " + actualE);
            return false;
        }
        return true;
    }

    public static boolean testLinhasVaziasSaoIgnoradas() throws Exception {
        List<String> lines = runConverterWithSyntheticInput("0 1\n\n1 2\n\n");
        int actualE = Integer.parseInt(lines.get(1));
        if (actualE != 2) {
            System.err.println("  E esperado 2, obtido " + actualE);
            return false;
        }
        return true;
    }

    public static boolean testUmaArestaApenas() throws Exception {
        List<String> lines = runConverterWithSyntheticInput("3 7\n");
        if (lines.size() < 3) {
            System.err.println("  Saida insuficiente.");
            return false;
        }

        int actualV = Integer.parseInt(lines.get(0));
        int actualE = Integer.parseInt(lines.get(1));
        String edge = lines.get(2);
        if (actualV != 8) {
            System.err.println("  V esperado 8, obtido " + actualV);
            return false;
        }
        if (actualE != 1) {
            System.err.println("  E esperado 1, obtido " + actualE);
            return false;
        }
        if (!edge.equals("3 7")) {
            System.err.println("  Aresta esperada '3 7', obtida '" + edge + "'");
            return false;
        }
        return true;
    }

    public static boolean testAlgs4ParsingVE() throws Exception {
        List<String> lines = runConverterWithSyntheticInput("0 1\n1 2\n2 5\n5 0\n");
        try {
            Graph g = toAlgs4Graph(lines);
            if (g.V() != 6) {
                System.err.println("  graph.V() esperado 6, obtido " + g.V());
                return false;
            }
            if (g.E() != 4) {
                System.err.println("  graph.E() esperado 4, obtido " + g.E());
                return false;
            }
        } catch (Exception e) {
            System.err.println("  Graph(In) lancou excecao: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean testAlgs4Adjacencia() throws Exception {
        List<String> lines = runConverterWithSyntheticInput("0 1\n1 2\n2 5\n5 0\n");
        try {
            Graph g = toAlgs4Graph(lines);
            if (!hasEdge(g, 0, 1)) {
                System.err.println("  Aresta 0-1 nao encontrada.");
                return false;
            }
            if (!hasEdge(g, 1, 2)) {
                System.err.println("  Aresta 1-2 nao encontrada.");
                return false;
            }
            if (!hasEdge(g, 2, 5)) {
                System.err.println("  Aresta 2-5 nao encontrada.");
                return false;
            }
            if (!hasEdge(g, 5, 0)) {
                System.err.println("  Aresta 5-0 nao encontrada.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("  Excecao ao verificar adjacencia: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean testArquivoRealMantemCabecalhoConsistente() throws Exception {
        GraphStats expected = computeStats(INPUT_PATH);
        runConverter();
        List<String> outputLines = readOutputLines();

        int actualV = Integer.parseInt(outputLines.get(0));
        int actualE = Integer.parseInt(outputLines.get(1));

        if (actualV != expected.vertices) {
            System.err.println("  V esperado " + expected.vertices + ", obtido " + actualV);
            return false;
        }
        if (actualE != expected.edges) {
            System.err.println("  E esperado " + expected.edges + ", obtido " + actualE);
            return false;
        }
        if (outputLines.size() != expected.edges + 2) {
            System.err.println("  Quantidade de linhas invalida na saida: " + outputLines.size());
            return false;
        }
        return true;
    }

    private static List<String> runConverterWithSyntheticInput(String inputData) throws Exception {
        byte[] originalInput = snapshotInput();
        try {
            Files.createDirectories(INPUT_PATH.getParent());
            Files.writeString(INPUT_PATH, inputData, StandardCharsets.UTF_8);
            runConverter();
            return readOutputLines();
        } finally {
            restoreInput(originalInput);
        }
    }

    private static void runConverter() throws IOException {
        Files.deleteIfExists(OUTPUT_PATH);
        EdgeListConverter.main(new String[0]);
        if (!Files.exists(OUTPUT_PATH)) {
            throw new IOException("Arquivo de saida nao foi gerado: " + OUTPUT_PATH);
        }
    }

    private static List<String> readOutputLines() throws IOException {
        List<String> raw = Files.readAllLines(OUTPUT_PATH, StandardCharsets.UTF_8);
        List<String> filtered = new ArrayList<>();
        for (String line : raw) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                filtered.add(trimmed);
            }
        }
        return filtered;
    }

    private static byte[] snapshotInput() throws IOException {
        if (!Files.exists(INPUT_PATH)) {
            return null;
        }
        return Files.readAllBytes(INPUT_PATH);
    }

    private static void restoreInput(byte[] originalInput) throws IOException {
        if (originalInput == null) {
            Files.deleteIfExists(INPUT_PATH);
            return;
        }
        Files.write(INPUT_PATH, originalInput);
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
                        // Mantem a regra atual: linha conta como aresta, mas nao altera maxVertexId.
                    }
                }
            }
        }

        return new GraphStats(maxVertexId + 1, edges);
    }

    private static Graph toAlgs4Graph(List<String> lines) {
        StringBuilder raw = new StringBuilder();
        for (String line : lines) {
            raw.append(line).append('\n');
        }
        In in = new In(new Scanner(raw.toString()));
        return new Graph(in);
    }

    private static boolean hasEdge(Graph g, int v, int w) {
        for (int neighbor : g.adj(v)) {
            if (neighbor == w) {
                return true;
            }
        }
        return false;
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
