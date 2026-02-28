package tests;

import edu.princeton.cs.algs4.Graph;
import java.util.HashSet;
import java.util.Set;

/**
 * DoD para JIRA-004.
 */
public class NeighborhoodTest {
    public static void main(String[] args) {
        Graph G = new Graph(5);
        G.addEdge(0, 1);
        G.addEdge(0, 4);
        G.addEdge(0, 2);

        Set<Integer> expected = new HashSet<>();
        expected.add(1); expected.add(2); expected.add(4);

        Set<Integer> actual = new HashSet<>();
        for (int w : G.adj(0)) {
            actual.add(w);
        }

        if (actual.equals(expected)) {
            System.out.println(">>> JIRA-004 PASSED!");
        } else {
            System.err.println(">>> JIRA-004 FAILED: Esperado " + expected + ", obtido " + actual);
            System.exit(1);
        }
    }
}
