# Referência de Implementação: EdgeListConverter

Este documento contém a implementação proposta para a classe `utils.EdgeListConverter`. Siga as instruções abaixo para aplicar as mudanças.

## Código Completo (`src/utils/EdgeListConverter.java`)

```java
package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Conversor específico para o dataset facebook_union.txt.
 * Implementa leitura de arquivo local e feedback visual de progresso.
 */
public class EdgeListConverter {

    // Caminhos fixos conforme solicitado
    private static final String INPUT_PATH = "data/union_data_facebook/facebook_union.txt";
    private static final String OUTPUT_PATH = "data/generated/facebook_union.txt";

    public static void main(String[] args) {
        ArrayList<String> edges = new ArrayList<>();
        int maxVertexId = -1;
        int count = 0;
        String anim = "|/-"; // Caracteres para a animação do loading

        System.out.println("Lendo arquivo: " + INPUT_PATH);

        // O 'try-with-resources' garante que o scanner seja fechado automaticamente
        try (Scanner scanner = new Scanner(new File(INPUT_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                edges.add(line);
                String[] parts = line.split("\s+");
                if (parts.length == 2) {
                    try {
                        int v = Integer.parseInt(parts[0]);
                        int w = Integer.parseInt(parts[1]);
                        // Atualiza o maior ID para calcular V depois
                        maxVertexId = Math.max(maxVertexId, Math.max(v, w));
                    } catch (NumberFormatException e) {
                        // Linha mal formatada é ignorada silenciosamente
                    }
                }

                // Feedback visual: atualiza a cada 10.000 arestas
                count++;
                if (count % 10000 == 0) {
                    //  move o cursor para o início da linha no terminal
                    System.err.print("Processando: " + anim.charAt((count / 10000) % anim.length()) + " " + count + " arestas lidas...");
                }
            }
            System.err.println("Concluído: " + count + " arestas processadas.          ");
            
        } catch (FileNotFoundException e) {
            System.err.println("Erro: Arquivo não encontrado em " + INPUT_PATH);
            return;
        }

        // V é o maior ID + 1 (pois os IDs começam em 0)
        int V = maxVertexId + 1;
        int E = edges.size();

        // Garante que a pasta de destino exista
        new File("data/generated").mkdirs();

        // Grava o arquivo formatado para a algs4
        try (PrintWriter out = new PrintWriter(new FileWriter(OUTPUT_PATH))) {
            out.println(V); // Primeira linha: Número de Vértices
            out.println(E); // Segunda linha: Número de Arestas
            for (String edge : edges) {
                out.println(edge); // Demais linhas: as arestas 'v w'
            }
            System.out.println("Sucesso! Grafo gerado em: " + OUTPUT_PATH);
            System.out.println("Estatísticas: V=" + V + ", E=" + E);
        } catch (IOException e) {
            System.err.println("Erro ao gravar arquivo: " + e.getMessage());
        }
    }
}
```

## Explicação dos Pontos Chave

1.  **`` (Carriage Return):** Usado no `System.err.print`. Ele faz o cursor voltar para o início da linha atual, permitindo que o contador de progresso seja atualizado sem "rolar" a tela.
2.  **`maxVertexId`:** Essencial para a `algs4`. Se o maior ID for 4038, o grafo precisa de 4039 posições de memória (do 0 ao 4038).
3.  **`System.err` vs `System.out`:** O progresso vai para o `err` para não interferir na saída de dados do programa caso você queira usar redirecionamento de logs.
4.  **`try-with-resources`:** Note o `try (Scanner ...)` — em Java, isso é a forma segura de abrir arquivos, garantindo que eles sejam fechados mesmo se ocorrer um erro.
