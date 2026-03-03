package tests;

import edu.princeton.cs.algs4.Graph;
import app.FacebookGraph;
import app.types.CRSData;
import java.util.List;
import java.util.BitSet;

/**
 * Teste Unificado de Representações de Grafo.
 * Valida todas as conversões de formato (Matrix, Incidence, EdgeList, CSR, etc).
 * 
 * Substitui: AdjListTest, AdjacencyMatrixTest, IncidenceMatrixTest, EdgeListTest e CSRTest.
 */
public class GraphRepresentationsTest {

    public static void main(String[] args) {
        System.out.println(">>> Iniciando Testes de Representações e Conversões...");

        // Setup: Grafo de teste 0-1-2 (Triângulo aberto/Caminho)
        int V = 3;
        Graph G = new Graph(V);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        // E = 2 arestas

        FacebookGraph fb = new FacebookGraph(G);
        boolean success = true;

        // 1. Lista de Adjacência
        List<Integer>[] adj = fb.toAdjacencyList();
        if (adj.length != 3 || adj[0].size() != 1 || adj[1].size() != 2) {
            System.err.println("FALHA: Lista de Adjacência incorreta.");
            success = false;
        }

        // 2. Matriz de Adjacência (Full e BitSet)
        boolean[][] matrix = fb.toAdjacencyMatrix();
        BitSet bitset = fb.toAdjacencyBitSet();
        if (!matrix[0][1] || !matrix[1][0] || matrix[0][2]) {
            System.err.println("FALHA: Matriz de Adjacência (boolean[][]) incorreta.");
            success = false;
        }
        if (!bitset.get(0 * V + 1) || !bitset.get(1 * V + 0) || bitset.get(0 * V + 2)) {
            System.err.println("FALHA: Matriz de Adjacência (BitSet) incorreta.");
            success = false;
        }

        // 3. Matriz de Incidência (V x E)
        boolean[][] incidence = fb.toIncidenceMatrix();
        // Coluna 0 (aresta 0-1): deve ter 1 nos nós 0 e 1
        boolean edge0Ok = (incidence[0][0] && incidence[1][0] && !incidence[2][0]);
        if (incidence.length != 3 || incidence[0].length != 2 || !edge0Ok) {
            System.err.println("FALHA: Matriz de Incidência incorreta.");
            success = false;
        }

        // 4. Lista de Arestas (E x 2)
        int[][] edges = fb.toEdgeList();
        if (edges.length != 2 || (edges[0][0] != 0 && edges[0][1] != 1 && edges[0][0] != 1)) {
            System.err.println("FALHA: Lista de Arestas (EdgeList) incorreta.");
            success = false;
        }

        // 5. CSR (Compressed Sparse Row)
        CRSData csr = fb.toCRS(false); // Modo não-direcionado (2E entradas)
        int[] expectedOffsets = {0, 1, 3, 4}; // Graus: 0->1, 1->2, 2->1. Acumulado: 1, 3, 4.
        for (int i = 0; i < expectedOffsets.length; i++) {
            if (csr.offsets()[i] != expectedOffsets[i]) {
                System.err.println("FALHA: CSR Offsets incorretos no índice " + i);
                success = false;
            }
        }
        if (csr.edges().length != 4) {
            System.err.println("FALHA: CSR Edges deveria ter 2*E = 4 entradas.");
            success = false;
        }

        // Resultado Final
        if (success) {
            System.out.println(">>> [OK] GraphRepresentationsTest PASSOU!");
        } else {
            System.err.println(">>> [ERRO] GraphRepresentationsTest FALHOU!");
            System.exit(1);
        }
    }
}
