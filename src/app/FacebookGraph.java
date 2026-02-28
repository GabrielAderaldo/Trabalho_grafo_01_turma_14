package app;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import edu.princeton.cs.algs4.Graph;

/**
 * Wrapper para analise do dataset do Facebook.
 * Centraliza a logica de calculo de metricas manuais.
 */
public class FacebookGraph {
    private final Graph G;

    // Criando a Factory Method para criar o grafo a partir de um objeto In e
    // ultilizar a classe já existente do algs4 para ler o grafo
    public static FacebookGraph fromIn(edu.princeton.cs.algs4.In in) {
        edu.princeton.cs.algs4.Graph G = new edu.princeton.cs.algs4.Graph(in);
        return new FacebookGraph(G);
    }

    public FacebookGraph(Graph G) {
        this.G = G;
    }

    public int V() {
        return G.V();
    }

    public int E() {
        return G.E();
    }

    public Iterable<Integer>[] adjList() {
        return java.util.stream.IntStream.range(0, G.V())
                .mapToObj(vertice -> {
                    List<Integer> neighbors = new java.util.ArrayList<>();
                    G.adj(vertice).forEach(neighbors::add);
                    return neighbors;
                }).toArray(Iterable[]::new);
    }

    public String toStringAdjacencyList() {
        return java.util.stream.IntStream.range(0, G.V()).mapToObj(v -> {
            String neighbors = java.util.stream.StreamSupport.stream(G.adj(v).spliterator(), false)
                    .map(String::valueOf)
                    .collect(java.util.stream.Collectors.joining(", "));
            return "Vertice " + v + ": " + neighbors;
        }).collect(java.util.stream.Collectors.joining("\n"));
    }

    /**
     * Exporta o grafo no formato de alta performance CSR (Compressed Sparse Row).
     */
    public void exportCSR(String path, boolean isDirected) {
        final int[] edges = isDirected ? new int[G.E()] : new int[2 * G.E()];
        final int[] offsets = new int[G.V() + 1];

        offsets[0] = 0;
        for (int v = 0; v < G.V(); v++) {
            offsets[v + 1] = offsets[v] + G.degree(v);
        }

        int current = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                edges[current++] = w;
            }
        }

        try (DataOutputStream dos = new DataOutputStream(
                new java.io.BufferedOutputStream(new java.io.FileOutputStream(path)))) {
            dos.writeInt(G.V());
            dos.writeInt(G.E());
            for (int offset : offsets) {
                dos.writeInt(offset);
            }
            for (int edge : edges) {
                dos.writeInt(edge);
            }
        } catch (java.io.IOException e) {
            System.err.println("Erro ao exportar grafo em CSR: " + e.getMessage());
        }
    }

    /**
     * Retorna a densidade do grafo.
     */
    public double density() {
        // Implementar manualmente: 2.0 * E / (V * (V-1))
        return 0.0;
    }

    /**
     * Retorna o grau maximo encontrado no grafo.
     * Artur
     */
    public int maxDegree() {
        // Implementar percorrendo todos os vertices
        return 0;
    }

    /**
     * Retorna o grau minimo encontrado no grafo.
     * Artur
     */
    public int minDegree() {
        return 0;
    }

    /**
     * Retorna o grau medio.
     */
    public double avgDegree() {
        return 0.0;
    }

    /**
     * RESPONSAVEL: GABRIEL ADERALDO
     * Conta o numero de componentes conexos usando DFS manual.
     */
    public int countComponents() {
        return 0;
    }

    /**
     * Gabriel Aderaldo
     * Verifica se o grafo e bipartido usando 2-coloracao manual.
     */
    public boolean isBipartite() {
        return false;
    }

    /**
     * Retorna os vizinhos de um vertice (Lista de Adjacencia).
     */
    public Iterable<Integer> adj(int v) {
        return G.adj(v);
    }

    /**
     * Retorna a Matriz de Adjacencia (V x V).
     * Cuidado: Requer ~16MB de memoria para este dataset.
     */
    public boolean[][] toAdjacencyMatrix() {
        final int v = G.V();
        boolean[][] matrix = new boolean[v][v];
        for (int i = 0; i < v; i++) {
            for (int w : G.adj(i)) {
                matrix[i][w] = true;
            }
        }
        return matrix;
    }

    public void exportAdjacencyMatrix(String path) {
        int V = G.V();
        java.util.BitSet bits = new java.util.BitSet(V * V);
        for (int v = 0; v < V; v++) {
            for (int w : G.adj(v)) {
                bits.set(v * V + w);
            }
        }
        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(path)) {
            // Grava V primeiro
            java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);
            dos.writeInt(V);
            // Grava os bytes do BitSet
            fos.write(bits.toByteArray());
        } catch (java.io.IOException e) {
            System.err.println("Erro ao exportar matriz de adjacencia: " + e.getMessage());
        }
    }

    /**
     * Retorna a Matriz de Incidencia (V x E).
     * AVISO: Requer ~350MB de memoria para este dataset.
     */
    public boolean[][] toIncidenceMatrix() {
        final int v = G.V();
        final int e = G.E();
        boolean[][] matrix = new boolean[v][e];
        int edgeIndex = 0;
        for (int i = 0; i < v; i++) {
            for (int w : G.adj(i)) {
                if (i < w) {
                    matrix[i][edgeIndex] = true;
                    matrix[w][edgeIndex] = true;
                    edgeIndex++;
                }
            }
        }
        return matrix;
    }

    public void exportIncidenceMatrixBinary(String path) {
        try (java.io.DataOutputStream dos = new java.io.DataOutputStream(
                new java.io.BufferedOutputStream(new java.io.FileOutputStream(path)))) {

            dos.writeInt(G.V());
            dos.writeInt(G.E());
            for (int v = 0; v < G.V(); v++) {
                for (int w : G.adj(v)) {
                    if (v < w) { // Pega cada aresta apenas uma vez
                        dos.writeInt(v);
                        dos.writeInt(w);
                    }
                }
            }
        } catch (java.io.IOException e) {
            System.err.println("Erro ao exportar matriz de incidencia: " + e.getMessage());
        }
    }

    /**
     * Retorna a Lista de Arestas (Edge List).
     */
    public int[][] toEdgeList() {
        final int[][] edges = new int[G.E()][2];
        int counter = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (v < w) {
                    edges[counter][0] = v;
                    edges[counter][1] = w;
                    counter++;
                }
            }
        }
        return edges;
    }

    public void exportEdgeListBinary(String path) {
        try (java.io.DataOutputStream dos = new java.io.DataOutputStream(
                new java.io.BufferedOutputStream(new java.io.FileOutputStream(path)))) {
            dos.writeInt(G.V());
            dos.writeInt(G.E());
            for (int v = 0; v < G.V(); v++) {
                for (int w : G.adj(v)) {
                    if (v < w) { // Pega cada aresta apenas uma vez
                        dos.writeInt(v);
                        dos.writeInt(w);
                    }
                }
            }
        } catch (java.io.IOException e) {
            System.err.println("Erro ao exportar edge list: " + e.getMessage());
        }
    }
}
