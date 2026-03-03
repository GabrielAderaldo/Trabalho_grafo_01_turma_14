package tests;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import utils.EdgeListConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Teste Unificado de Integração de Entrada e Saída (I/O).
 */
public class IOIntegrationTest {

    private static final Path INPUT_PATH = Paths.get("data/union_data_facebook/facebook_union.txt");
    private static final Path OUTPUT_PATH = Paths.get("data/generated/facebook_union.txt");

    public static void main(String[] args) {
        System.out.println(">>> Iniciando Testes de Integração de I/O (Pontas-a-Ponta)...");

        boolean success = true;

        try {
            if (!Files.exists(INPUT_PATH)) {
                System.err.println("FALHA: Arquivo original não encontrado em: " + INPUT_PATH);
                System.exit(1);
            }

            System.out.println("    Executando Conversão (EdgeListConverter)...");
            EdgeListConverter.main(new String[0]);
            
            if (!Files.exists(OUTPUT_PATH)) {
                System.err.println("FALHA: Arquivo de saída algs4 não foi gerado.");
                success = false;
            }

            List<String> lines = Files.readAllLines(OUTPUT_PATH);
            int expectedV = 4039;
            int expectedE = 88234;
            
            int actualV = Integer.parseInt(lines.get(0).trim());
            int actualE = Integer.parseInt(lines.get(1).trim());

            if (actualV != expectedV || actualE != expectedE) {
                System.err.println("FALHA: Cabeçalho V/E incorreto. Esperado: " + expectedV + "V, " + expectedE + "E");
                success = false;
            }

            System.out.println("    Validando carregamento pela biblioteca algs4...");
            In in = new In(OUTPUT_PATH.toString());
            Graph G = new Graph(in);
            
            if (G.V() != expectedV || G.E() != expectedE) {
                System.err.println("FALHA: O objeto Graph da algs4 carregou dados divergentes.");
                success = false;
            }

            if (G.degree(0) == 0) {
                System.err.println("FALHA: O nó ego central (0) deveria ter muitos vizinhos.");
                success = false;
            }

            if (success) {
                System.out.println("\n>>> [OK] IOIntegrationTest PASSOU!");
                System.out.println("    V: " + G.V() + ", E: " + G.E() + " (Dados SNAP validados)");
            } else {
                System.err.println("\n>>> [ERRO] IOIntegrationTest FALHOU!");
                System.exit(1);
            }

        } catch (Exception e) {
            System.err.println("\n>>> [ERRO] IOIntegrationTest FALHOU: " + e.getMessage());
            System.exit(1);
        }
    }
}
