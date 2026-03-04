/******************************************************************************
 *  Compilation:  javac FacebookGraph.java
 *  Execution:    java FacebookGraph
 *  Dependencies: Graph.java In.java
 *  Data files:   data/generated/facebook_union.txt
 *
 *  Wrapper class for analyzing Facebook ego networks from SNAP.
 *  Provides manual implementations of fundamental graph algorithms and 
 *  various computational representations (Adjacency Matrix, Incidence Matrix, etc.)
 *
 ******************************************************************************/

package app;

import java.util.BitSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.IntStream;

import app.types.CRSData;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

/**
 *  The {@code FacebookGraph} class represents a data type for analyzing 
 *  social network graphs. It wraps the {@link Graph} class and provides
 *  manual implementations of statistical and topological metrics.
 *  <p>
 *  This implementation is part of the investigation into Scale-Free Networks
 *  using the SNAP dataset. It emphasizes manual algorithm verification 
 *  over library-ready solutions for educational purposes, avoiding built-in
 *  methods like {@code G.degree(v)} or {@code G.isBipartite()}.
 *  <p>
 *  For additional documentation, see 
 *  <a href="https://algs4.cs.princeton.edu/41graph">Section 4.1</a> 
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Gabriel Aderaldo
 *  @author Arthur Alves
 *  @author Lucas Magalhães
 */
public class FacebookGraph {
    private final Graph G;

    /**
     * Initializes a {@code FacebookGraph} from an input stream.
     * The input format must follow the algs4 standard (V, E, then edge list).
     *
     * @param  in the input stream
     * @return a new FacebookGraph instance
     */
    public static FacebookGraph fromIn(In in) {
        return new FacebookGraph(new Graph(in));
    }

    /**
     * Initializes a {@code FacebookGraph} from an existing {@code Graph}.
     *
     * @param  G the undirected graph
     */
    public FacebookGraph(Graph G) {
        this.G = G;
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices (Order)
     */
    public int V() { return G.V(); }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges (Size)
     */
    public int E() { return G.E(); }

    /**
     * Returns the graph density.
     * The density is the ratio of edges to the maximum possible edges.
     * Uses the formula: 2E / (V * (V - 1)) for undirected graphs.
     *
     * @return the density as a double between 0 and 1
     */
    public double density() {
        int V = G.V();
        int E = G.E();
        
        if (V <= 1) return 0.0;
        
        return (2.0 * E) / (V * (V - 1));
    }

    /**
     * Returns the maximum degree in the graph.
     * Calculated by manually iterating through the adjacency lists.
     * Runs in &Theta;(V + E) time.
     *
     * @return the maximum degree
     */
    public int maxDegree() {
        int max = 0;
        for (int v = 0; v < G.V(); v++) {
            int degree = 0;
            for (int w : G.adj(v)) {
                degree++;
            }
            if (degree > max) max = degree;
        }
        return max;
    }

    /**
     * Returns the minimum degree in the graph.
     * Calculated by manually iterating through the adjacency lists.
     * Runs in &Theta;(V + E) time.
     *
     * @return the minimum degree
     */
    public int minDegree() {
        if (G.V() == 0) return 0;

        int min = Integer.MAX_VALUE;
        for (int v = 0; v < G.V(); v++) {
            int degree = 0;
            for (int w : G.adj(v)) {
                degree++;
            }
            if (degree < min) min = degree;
        }
        return min;
    }

    /**
     * Returns the average degree of the vertices.
     * Calculated by manually summing the degrees of all vertices.
     *
     * @return the average degree
     */
    public double avgDegree() {
        if (G.V() == 0) return 0.0;

        int totalDegree = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                totalDegree++;
            }
        }
        return (double) totalDegree / G.V();
    }

    /**
     * Returns the graph as an array of adjacency lists.
     * Takes &Theta;(V + E) time and uses &Theta;(V + E) extra space.
     *
     * @return the array of lists
     */
    @SuppressWarnings("unchecked")
    public List<Integer>[] toAdjacencyList() {
        List<Integer>[] adj = (List<Integer>[]) new ArrayList[G.V()];
        for (int v = 0; v < G.V(); v++) {
            adj[v] = new ArrayList<>();
            for (int w : G.adj(v)) {
                adj[v].add(w);
            }
        }
        return adj;
    }

    /**
     * Returns the graph as a two-dimensional adjacency matrix.
     * Takes &Theta;(V + E) time and uses &Theta;(V^2) extra space.
     *
     * @return the boolean matrix
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

    /**
     * Returns a bit-compressed adjacency matrix.
     * Uses &Theta;(V^2 / 8) bytes of memory via {@link BitSet}.
     *
     * @return the BitSet representation
     */
    public BitSet toAdjacencyBitSet() {
        final int v = G.V();
        BitSet bitset = new BitSet(v * v);
        for (int i = 0; i < v; i++) {
            for (int w : G.adj(i)) {
                bitset.set(i * v + w);
            }
        }
        return bitset;
    }

    /**
     * Returns the graph as a two-dimensional incidence matrix (V x E).
     * Takes &Theta;(V + E) time and uses &Theta;(V * E) space.
     *
     * @return the boolean incidence matrix
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

    /**
     * Returns the list of edges as a two-dimensional integer array.
     *
     * @return the edge list [E][2]
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

    /**
     * Converts the graph to CSR (Compressed Sparse Row) format.
     *
     * @param  isDirected true if the graph should be treated as directed (E entries),
     *                    false for undirected (2E entries).
     * @return the CSR data structure
     */
    public CRSData toCRS(boolean isDirected) {
        int totalEntries = isDirected ? G.E() : 2 * G.E();
        final int[] edges = new int[totalEntries];
        final int[] offsets = new int[G.V() + 1];

        offsets[0] = 0;
        for (int v = 0; v < G.V(); v++) {
            int count = 0;
            for (int w : G.adj(v)) {
                if (!isDirected || v < w) count++;
            }
            offsets[v + 1] = offsets[v] + count;
        }

        int current = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (!isDirected || v < w) {
                    edges[current++] = w;
                }
            }
        }
        return new CRSData(edges, offsets);
    }

    /**
     * Returns the number of connected components in the graph.
     * Implementation uses manual iterative depth-first search to avoid stack overflow.
     *
     * @return the number of connected components
     */
    public int countComponents() {
        BitSet visited = new BitSet(G.V());
        return (int) IntStream.range(0, G.V())
            .filter(v -> {
                if (!visited.get(v)) {
                    dfs_iterative(v, visited);
                    return true;
                }
                return false;
            }).count();
    }

    /**
     * Returns true if the graph is bipartite.
     * Uses a manual two-coloring algorithm via iterative DFS to avoid stack overflow.
     *
     * @return {@code true} if bipartite; {@code false} otherwise
     */
    public boolean isBipartite() {
        int V = G.V();
        boolean[] marked = new boolean[V];
        boolean[] color = new boolean[V];
        
        for (int s = 0; s < V; s++) {
            if (!marked[s]) {
                Stack<Integer> stack = new Stack<>();
                stack.push(s);
                marked[s] = true;
                while (!stack.isEmpty()) {
                    int v = stack.pop();
                    for (int w : G.adj(v)) {
                        if (!marked[w]) {
                            marked[w] = true;
                            color[w] = !color[v];
                            stack.push(w);
                        } else if (color[w] == color[v]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Performs a manual iterative depth-first search from a starting node.
     *
     * @param startNode the vertex to start the DFS from
     * @param visited   the BitSet tracking visited vertices
     */
    private void dfs_iterative(int startNode, BitSet visited) {
        Stack<Integer> stack = new Stack<>();
        stack.push(startNode);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited.get(v)) {
                visited.set(v);
                for (int w : G.adj(v)) {
                    if (!visited.get(w)) {
                        stack.push(w);
                    }
                }
            }
        }
    }

    /**
     * Calculates the average clustering coefficient of the graph.
     * The clustering coefficient of a vertex measures how close its neighbors are to being a clique.
     * Calculated as the average of the local clustering coefficients of all vertices.
     * 
     * Runs in &Theta;(V * k^2) where k is the average degree.
     *
     * @return the average clustering coefficient as a double between 0 and 1
     */
    public double avgClusteringCoefficient() {
        if (this.G.V() == 0) return 0.0;

        // Utilizamos o BitSet para busca rápida O(1) de existência de arestas
        BitSet adjMatrix = this.toAdjacencyBitSet();
        double totalClusteringCoefficient = 0.0;
        int V = this.G.V();
        
        for (int v = 0; v < V; v++) {
            List<Integer> neighbors = new ArrayList<>();
            for (int w : G.adj(v)) neighbors.add(w);
            
            int k = neighbors.size();
            if (k < 2) continue;
            
            int edgesBetweenNeighbors = 0;
            for (int i = 0; i < k; i++) {
                for (int j = i + 1; j < k; j++) {
                    // Verifica se existe aresta entre o vizinho i e o vizinho j
                    if (adjMatrix.get(neighbors.get(i) * V + neighbors.get(j))) {
                        edgesBetweenNeighbors++;
                    }
                }
            }
            
            double clusteringCoefficient = (2.0 * edgesBetweenNeighbors) / (k * (k - 1));
            totalClusteringCoefficient += clusteringCoefficient;
        }
        return totalClusteringCoefficient / V;
    }

    /**
     * Returns the vertices adjacent to vertex {@code v}.
     *
     * @param  v the vertex
     * @return the vertices adjacent to vertex {@code v} as an iterable
     */
    public Iterable<Integer> adj(int v) {
        return G.adj(v);
    }
}
