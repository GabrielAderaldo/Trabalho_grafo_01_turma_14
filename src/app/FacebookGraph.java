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
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;

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
 *  over library-ready solutions for educational purposes.
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
     *
     * @return the density as a double between 0 and 1
     */
    public double density() {
        // TODO: Implementar manualmente (Arthur Alves)
        return 0.0;
    }

    /**
     * Returns the maximum degree in the graph.
     * Runs in &Theta;(V + E) time.
     *
     * @return the maximum degree
     */
    public int maxDegree() {
        // TODO: Implementar (Arthur Alves)
        return 0;
    }

    /**
     * Returns the minimum degree in the graph.
     * Runs in &Theta;(V + E) time.
     *
     * @return the minimum degree
     */
    public int minDegree() {
        // TODO: Implementar (Arthur Alves)
        return 0;
    }

    /**
     * Returns the average degree of the vertices.
     *
     * @return the average degree
     */
    public double avgDegree() {
        // TODO: Implementar (Arthur Alves)
        return 0.0;
    }

    /**
     * Returns the graph as an array of adjacency lists.
     * Takes &Theta;(V + E) time and uses &Theta;(V + E) extra space.
     *
     * @return the array of lists
     */
    public List<Integer>[] toAdjacencyList() {
        return IntStream.range(0, G.V())
                .mapToObj(v -> {
                    List<Integer> neighbors = new ArrayList<>();
                    G.adj(v).forEach(neighbors::add);
                    return neighbors;
                }).toArray(List[]::new);
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
     * Uses &Theta;(V^2 / 8) bytes of memory.
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
     * @param  isDirected true if the graph should be treated as directed
     * @return the CSR data structure
     */
    public CRSData toCRS(boolean isDirected) {
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
        return new CRSData(edges, offsets);
    }

    /**
     * Returns the number of connected components in the graph.
     * Implementation uses iterative depth-first search.
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
     * Uses a manual two-coloring algorithm via DFS.
     *
     * @return {@code true} if bipartite; {@code false} otherwise
     */
    public boolean isBipartite() {
        int V = G.V();
        boolean[] marked = new boolean[V];
        boolean[] color = new boolean[V];
        return IntStream.range(0, V)
            .filter(v -> !marked[v])
            .allMatch(v -> checkBipartite(v, marked, color));
    }

    private void dfs_iterative(int startNode, BitSet visited) {
        Stack<Integer> stack = new Stack<>();
        stack.push(startNode);
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited.get(v)) {
                visited.set(v);
                StreamSupport.stream(G.adj(v).spliterator(), false)
                    .filter(w -> !visited.get(w))
                    .forEach(stack::push);
            }
        }
    }

    private boolean checkBipartite(int v, boolean[] marked, boolean[] color) {
        marked[v] = true;
        return StreamSupport.stream(G.adj(v).spliterator(), false)
            .allMatch(w -> {
                if (!marked[w]) {
                    color[w] = !color[v];
                    return checkBipartite(w, marked, color);
                } 
                return color[w] != color[v];
            });
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
