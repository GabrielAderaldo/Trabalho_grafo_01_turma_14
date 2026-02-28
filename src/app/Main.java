package app;
import java.io.File;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Graph;

public class Main {
    public static void main(String[] args) {
        String path = "data/generated/facebook_union.txt";
        java.io.File file = new File(path);

        if (!file.exists()) { 
            throw new RuntimeException("Arquivo nao encontrado: " + path); 
        }

        In in = new In(path);
        final FacebookGraph fbGraph = FacebookGraph.fromIn(in);
        fbGraph.exportIncidenceMatrixBinary("data/generated/facebook_union.incidence.edges.bin");
    }
}
