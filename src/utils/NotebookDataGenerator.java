package utils;

import app.FacebookGraph;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

/**
 * Gerador de Artefatos para Jupyter Notebook.
 * Foco em Formatos Binários de Alta Performance.
 */
public class NotebookDataGenerator {

  public static void generateAll(FacebookGraph fb, String prefix) {
    int[] degrees = getDegreesArray(fb);

    // 1. Estatísticas Leves (JSON/CSV)
    exportGlobalMetrics(prefix, fb);
    DataExporter.exportRawVertexDegrees(prefix, degrees);
    exportFrequencies(prefix, degrees);

    // 2. Formatos Binários (O Coração da Performance)
    // Edge List: [V, E, edges...]
    DataExporter.toBinaryEdgeList(prefix, fb.V(), fb.E(), fb.toEdgeList());
    
    // Adjacency Matrix (Bitset): [V, bits...]
    DataExporter.toBitset(prefix, fb.V(), fb.toAdjacencyBitSet());

    // CSR (Compressed Sparse Row): [V, E, offsets..., edges...]
    app.types.CRSData csr = fb.toCRS(false);
    DataExporter.exportCSR(prefix, fb.V(), fb.E(), csr.offsets(), csr.edges());
  }

  private static int[] getDegreesArray(FacebookGraph fb) {
    int V = fb.V();
    int[] degrees = new int[V];
    for (int v = 0; v < V; v++) {
      int count = 0;
      for (int w : fb.adj(v)) count++;
      degrees[v] = count;
    }
    return degrees;
  }

  private static void exportFrequencies(String prefix, int[] degrees) {
    Map<Integer, Integer> freqMap = new TreeMap<>();
    for (int d : degrees) freqMap.put(d, freqMap.getOrDefault(d, 0) + 1);
    List<String> rows = new ArrayList<>();
    freqMap.forEach((grau, qtd) -> rows.add(grau + "," + qtd));
    DataExporter.toCSV(prefix + "_degree_frequencies", "Grau,Quantidade", rows);
  }

  private static void exportGlobalMetrics(String prefix, FacebookGraph fb) {
    Map<String, Object> metrics = new HashMap<>();
    metrics.put("v_order", fb.V());
    metrics.put("e_size", fb.E());
    metrics.put("density", String.format(java.util.Locale.US, "%.6f", fb.density()));
    metrics.put("max_degree", fb.maxDegree());
    metrics.put("min_degree", fb.minDegree());
    metrics.put("avg_degree", String.format(java.util.Locale.US, "%.2f", fb.avgDegree()));
    DataExporter.toJSON(prefix + "_metrics", metrics);
  }
}
