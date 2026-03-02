package utils;

import java.io.*;
import java.util.BitSet;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hub Central de Exportação.
 * Camada de I/O blindada que garante a criação automática de diretórios.
 */
public class DataExporter {

    private static String outputBase = "data/generated/";

    /**
     * Define se o exportador deve operar em modo de teste.
     * Se true, os arquivos serão salvos em data/generated/test/
     */
    public static void setTestMode(boolean isTest) {
        outputBase = isTest ? "data/generated/test/" : "data/generated/";
    }

    // --- MÉTODOS AUXILIARES ---

    /**
     * Garante que o diretório pai de um arquivo exista.
     */
    private static void ensureDirectoryExists(String path) {
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    // --- FORMATOS BINÁRIOS (ALTA PERFORMANCE) ---

    public static void exportCSR(String nameOfFile, int V, int E, int[] offsets, int[] edges) {
        String path = outputBase + "bin/" + nameOfFile + "_csr.bin";
        ensureDirectoryExists(path);
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            dos.writeInt(V);
            dos.writeInt(E);
            for (int offset : offsets) dos.writeInt(offset);
            for (int edge : edges) dos.writeInt(edge);
        } catch (IOException e) {
            System.err.println("Erro ao exportar CSR: " + e.getMessage());
        }
    }

    public static void toBitset(String nameOfFile, int V, BitSet bits) {
        String path = outputBase + "bin/" + nameOfFile + "_adjmatrix.bin";
        ensureDirectoryExists(path);
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            dos.writeInt(V);
            dos.write(bits.toByteArray());
        } catch (IOException e) {
            System.err.println("Erro ao exportar Bitset: " + e.getMessage());
        }
    }

    public static void toBinaryEdgeList(String nameOfFile, int V, int E, int[][] edges) {
        String path = outputBase + "bin/" + nameOfFile + "_edgelist.bin";
        ensureDirectoryExists(path);
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            dos.writeInt(V);
            dos.writeInt(E);
            for (int[] pair : edges) {
                dos.writeInt(pair[0]);
                dos.writeInt(pair[1]);
            }
        } catch (IOException e) {
            System.err.println("Erro ao exportar Binary Edge List: " + e.getMessage());
        }
    }

    // --- FORMATOS TEXTUAIS (DATA SCIENCE / NOTEBOOK) ---

    public static void toCSV(String nameOfFile, String header, List<String> rows) {
        String path = outputBase + "sheets/" + nameOfFile + ".csv";
        ensureDirectoryExists(path);
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path)))) {
            if (header != null && !header.isEmpty()) out.println(header);
            rows.forEach(out::println);
        } catch (IOException e) {
            System.err.println("Erro ao exportar CSV: " + e.getMessage());
        }
    }

    public static void toJSON(String nameOfFile, Map<String, Object> metrics) {
        String path = outputBase + "plain_text/" + nameOfFile + ".json";
        ensureDirectoryExists(path);
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path)))) {
            String body = metrics.entrySet().stream()
                .map(e -> {
                    String value = e.getValue().toString().replace(',', '.');
                    return String.format("  \"%s\": %s", e.getKey(), value);
                })
                .collect(Collectors.joining(",\n"));
            out.println("{\n" + body + "\n}");
        } catch (IOException e) {
            System.err.println("Erro ao exportar JSON: " + e.getMessage());
        }
    }

    public static void exportRawVertexDegrees(String nameOfFile, int[] degrees) {
        String path = outputBase + "sheets/" + nameOfFile + "_vertex_degrees.csv";
        ensureDirectoryExists(path);
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path)))) {
            out.println("Vertice,Grau");
            for (int i = 0; i < degrees.length; i++) {
                out.printf("%d,%d\n", i, degrees[i]);
            }
        } catch (IOException e) {
            System.err.println("Erro ao exportar graus brutos: " + e.getMessage());
        }
    }
}
