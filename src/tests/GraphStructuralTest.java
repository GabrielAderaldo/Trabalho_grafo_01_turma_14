package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import java.util.HashSet;
import java.util.Set;

/**
 * Teste Unificado de Estrutura e Métricas do Grafo.
 * Valida: Ordem (V), Tamanho (E), Densidade, Graus (Max, Min, Médio) e Vizinhança (Adj).
 * 
 * Substitui: DegreeStatsTest, GraphMetricsComparisonTest e NeighborhoodTest.
 */
public class GraphStructuralTest {

    public static void main(String[] args) {
        System.out.println(">>> Iniciando Testes Estruturais (Métricas e Adjacência)...");

        // Setup: Grafo de teste controlado
        int V = 6;
        Graph G = new Graph(V);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 4);
        G.addEdge(3, 5);
        // E = 6 arestas

        FacebookGraph fb = new FacebookGraph(G);
        boolean success = true;

        // 1. Validação de Ordem e Tamanho
        if (fb.V() != G.V() || fb.E() != G.E()) {
            System.err.println("FALHA: V ou E divergente do original.");
            success = false;
        }

        // 2. Validação de Densidade (Manual: 2E / (V * (V-1)))
        double expectedDensity = (2.0 * G.E()) / (G.V() * (G.V() - 1));
        if (Math.abs(fb.density() - expectedDensity) > 1e-10) {
            System.err.println("FALHA: Densidade incorreta. Esperado: " + expectedDensity);
            success = false;
        }

        // 3. Validação de Graus (Contagem Manual vs Wrapper)
        int manualMax = Integer.MIN_VALUE;
        int manualMin = Integer.MAX_VALUE;
        int totalDegree = 0;

        for (int v = 0; v < G.V(); v++) {
            int d = 0;
            for (int w : G.adj(v)) d++; // Contagem manual pura
            
            if (d > manualMax) manualMax = d;
            if (d < manualMin) manualMin = d;
            totalDegree += d;
        }
        double manualAvg = (double) totalDegree / G.V();

        if (fb.maxDegree() != manualMax || fb.minDegree() != manualMin || Math.abs(fb.avgDegree() - manualAvg) > 1e-10) {
            System.err.println("FALHA: Estatísticas de graus (max/min/avg) incorretas.");
            success = false;
        }

        // 4. Validação de Vizinhança (Neighborhood)
        Set<Integer> expectedAdj0 = new HashSet<>();
        expectedAdj0.add(1); expectedAdj0.add(2);
        
        Set<Integer> actualAdj0 = new HashSet<>();
        for (int w : fb.adj(0)) actualAdj0.add(w);

        if (!actualAdj0.equals(expectedAdj0)) {
            System.err.println("FALHA: Vizinhança do vértice 0 incorreta.");
            success = false;
        }

        // Resultado Final
        if (success) {
            System.out.println(">>> [OK] GraphStructuralTest PASSOU!");
            System.out.println("    V: " + fb.V() + ", E: " + fb.E() + ", Densidade: " + fb.density());
            System.out.println("    Grau Max: " + fb.maxDegree() + ", Min: " + fb.minDegree() + ", Médio: " + fb.avgDegree());
        } else {
            System.err.println(">>> [ERRO] GraphStructuralTest FALHOU!");
            System.exit(1);
        }
    }
}
