package tests;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.CC;
import edu.princeton.cs.algs4.Bipartite;
import app.FacebookGraph;

/**
 * Teste Unificado de Algoritmos de Grafo.
 */
public class GraphAlgorithmsTest {

    public static void main(String[] args) {
        System.out.println(">>> Iniciando Testes de Algoritmos (Busca e Estrutura)...");

        boolean success = true;

        System.out.println("  Teste 1: Grafo com 2 componentes (um triângulo)");
        Graph G1 = new Graph(6);
        G1.addEdge(0, 1); G1.addEdge(1, 2); G1.addEdge(2, 0);
        G1.addEdge(3, 4); G1.addEdge(4, 5);
        if (!validate(G1)) success = false;

        System.out.println("\n  Teste 2: Grafo Bipartido (Quadrado)");
        Graph G2 = new Graph(4);
        G2.addEdge(0, 1); G2.addEdge(1, 2); G2.addEdge(2, 3); G2.addEdge(3, 0);
        if (!validate(G2)) success = false;

        System.out.println("\n  Teste 3: Grafo com 5 componentes isolados");
        Graph G3 = new Graph(5);
        if (!validate(G3)) success = false;

        if (success) {
            System.out.println("\n>>> [OK] GraphAlgorithmsTest PASSOU!");
        } else {
            System.err.println("\n>>> [ERRO] GraphAlgorithmsTest FALHOU!");
            System.exit(1);
        }
    }

    private static boolean validate(Graph G) {
        FacebookGraph fb = new FacebookGraph(G);
        CC ccAlgs4 = new CC(G);
        int expectedCC = ccAlgs4.count();
        int actualCC = fb.countComponents();
        
        if (expectedCC != actualCC) {
            System.err.println("    FALHA CC: Esperado " + expectedCC + ", obtido " + actualCC);
            return false;
        }

        Bipartite bipAlgs4 = new Bipartite(G);
        boolean expectedBip = bipAlgs4.isBipartite();
        boolean actualBip = fb.isBipartite();
        
        if (expectedBip != actualBip) {
            System.err.println("    FALHA Bipartido: Esperado " + expectedBip + ", obtido " + actualBip);
            return false;
        }

        System.out.println("    Componentes: " + actualCC + " | Bipartido: " + actualBip + " [OK]");
        return true;
    }
}
