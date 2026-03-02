package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * DTO para agrupar métricas do grafo e facilitar exportação para JSON.
 */
public class GraphMetrics {

    private final int v;
    private final int e;
    private final double avgDegree;
    private final int maxDegree;
    private final int minDegree;
    private final double density;

    public GraphMetrics(int v, int e, double avgDegree, int maxDegree, int minDegree, double density) {
        this.v = v;
        this.e = e;
        this.avgDegree = avgDegree;
        this.maxDegree = maxDegree;
        this.minDegree = minDegree;
        this.density = density;
    }

    public int getV() { return v; }
    public int getE() { return e; }
    public double getAvgDegree() { return avgDegree; }
    public int getMaxDegree() { return maxDegree; }
    public int getMinDegree() { return minDegree; }
    public double getDensity() { return density; }

    /**
     * Converte para Map, pronto para uso em DataExporter.toJSON.
     */
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("v", v);
        m.put("e", e);
        m.put("avg_degree", avgDegree);
        m.put("max_degree", maxDegree);
        m.put("min_degree", minDegree);
        m.put("density", density);
        return m;
    }

    /**
     * Helper para exportar diretamente usando o DataExporter.
     */
    public void exportAsJson(String nameOfFile) {
        DataExporter.toJSON(nameOfFile, this.toMap());
    }
}

