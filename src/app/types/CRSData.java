package app.types;

/**
 * Data structure to hold CSR (Compressed Sparse Row) arrays.
 */
public class CRSData {
    private final int[] edges;
    private final int[] offsets;

    public CRSData(int[] edges, int[] offsets) {
        this.edges = edges;
        this.offsets = offsets;
    }

    public int[] edges() { return edges; }
    public int[] offsets() { return offsets; }
}
