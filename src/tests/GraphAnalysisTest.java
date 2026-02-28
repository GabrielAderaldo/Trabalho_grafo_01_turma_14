package tests;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import java.io.File;

/**
 * Teste para validar se a lógica de análise de grafos está correta.
 * Verifica os valores esperados para o dataset do Facebook Union.
 */
public class GraphAnalysisTest {

    public static void main(String[] args) {
        String path = "data/generated/facebook_union.txt";
        File file = new File(path);

        if (!file.exists()) {
            System.err.println("ERRO: O arquivo " + path + " nao existe. Rode 'make generate' primeiro.");
            System.exit(1);
        }

        System.out.println("Validando carregamento do Grafo...");
        In in = new In(path);
        Graph G = new Graph(in);

        boolean success = true;

        // Validação da Ordem e Tamanho
        if (G.V() != 4039) {
            System.err.println("FALHA: Ordem (V) incorreta. Esperado 4039, obtido " + G.V());
            success = false;
        }

        if (G.E() != 88234) {
            System.err.println("FALHA: Tamanho (E) incorreto. Esperado 88234, obtido " + G.E());
            success = false;
        }

        // Validação de Grau (Exemplo: Grau do vértice 0)
        // No arquivo original, o nó 0 é um ego central com muitos amigos.
        int degree0 = G.degree(0);
        if (degree0 == 0) {
            System.err.println("FALHA: Grau do vertice 0 nao pode ser zero.");
            success = false;
        }

        if (success) {
            System.out.println(">>> TESTE DE CARREGAMENTO PASSOU!");
            System.out.println("V: " + G.V());
            System.out.println("E: " + G.E());
        } else {
            System.out.println(">>> TESTE FALHOU!");
            System.exit(1);
        }
    }
}
