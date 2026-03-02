package utils;

import app.FacebookGraph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Gerador de Artefatos para Jupyter Notebook.
 * Centraliza a exportação de métricas estatísticas, distribuições de frequência
 * e dados brutos para análise de Escala Livre (Scale-Free Networks).
 * 
 * @author Gabriel Aderaldo
 */
public class NotebookDataGenerator {

  /**
   * Executa todas as exportações necessárias para o Notebook.
   * Gera: CSV de graus brutos, CSV de frequências e JSON de métricas globais.
   *
   * @param fb     O Wrapper do Grafo do Facebook
   * @param prefix Prefixo para os nomes dos arquivos (ex: "facebook_union")
   */
  public static void generateAll(FacebookGraph fb, String prefix) {
    // 1. Obter graus de todos os vértices (Respeitando JIRA-001/014)
    int[] degrees = getDegreesArray(fb);

    // 2. Formatos para Pandas (Estatística/Relatório)
    DataExporter.exportRawVertexDegrees(prefix, degrees);
    exportFrequencies(prefix, degrees);
    exportGlobalMetrics(prefix, fb);

    // 3. Formatos Binários para NumPy (Alta Performance - Requisito do BINARY_IMPORT_GUIDE)
    DataExporter.toBinaryEdgeList(prefix, fb.V(), fb.E(), fb.toEdgeList());
    DataExporter.toBitset(prefix, fb.V(), fb.toAdjacencyBitSet());
  }

  /**
   * Calcula o grau de cada vértice individualmente.
   * Risco Pedagógico: Itera sobre a adjacência manualmente.
   */
  private static int[] getDegreesArray(FacebookGraph fb) {
    int V = fb.V();
    int[] degrees = new int[V];
    for (int v = 0; v < V; v++) {
      int count = 0;
      for (int w : fb.adj(v)) {
        count++;
      }
      degrees[v] = count;
    }
    return degrees;
  }

  /**
   * Calcula a frequência de cada grau e exporta como CSV.
   * Essencial para o gráfico de Distribuição de Graus e análise Log-Log.
   */
  private static void exportFrequencies(String prefix, int[] degrees) {
    // TreeMap mantém os graus ordenados para facilitar o gráfico no Python
    Map<Integer, Integer> freqMap = new TreeMap<>();

    for (int d : degrees) {
      freqMap.put(d, freqMap.getOrDefault(d, 0) + 1);
    }

    List<String> rows = new ArrayList<>();
    freqMap.forEach((grau, qtd) -> rows.add(grau + "," + qtd));

    DataExporter.toCSV(prefix + "_degree_frequencies", "Grau,Quantidade", rows);
  }

  /**
   * Coleta métricas de resumo estrutural e exporta em JSON.
   */
  private static void exportGlobalMetrics(String prefix, FacebookGraph fb) {
    Map<String, Object> metrics = new HashMap<>();
    metrics.put("v_order", fb.V());
    metrics.put("e_size", fb.E());
    metrics.put("density", String.format(java.util.Locale.US, "%.6f", fb.density()));
    metrics.put("max_degree", fb.maxDegree());
    metrics.put("min_degree", fb.minDegree());
    metrics.put("avg_degree", String.format(java.util.Locale.US, "%.2f", fb.avgDegree()));

    // Adicione aqui métricas futuras (ex: clustering_coefficient)

    DataExporter.toJSON(prefix + "_metrics", metrics);
  }
}