/**
 * Represents an edge in a graph, defined by a pair of vertices.
 * The vertices are ordered such that the label of the start vertex is smaller
 * than the label of the end vertex, ensuring consistency with graph requirements.
 *
 * @param <F> the type of the labels for vertices in the graph, which must be comparable.
 *
 * @author Renos Kerkides
 * @studentNumber 2923219K
 */
public class Edge<F extends Comparable<F>> implements Comparable<Edge<F>> {
    private final Vertex<F> v1; // Start vertex, label is smaller
    private final Vertex<F> v2; // End vertex, label is larger

    /**
     * Constructs an edge with the given start and end vertices.
     * Automatically orders vertices to maintain graph invariants.
     *
     * @param v1 The start vertex of the edge.
     * @param v2 The end vertex of the edge.
     */
    public Edge(Vertex<F> v1, Vertex<F> v2) {
        if (v1.compareTo(v2) < 0) {
            this.v1 = v1;
            this.v2 = v2;
        } else {
            this.v1 = v2;
            this.v2 = v1;
        }
    }

    /**
     * Returns the start vertex of the edge.
     *
     * @return The start vertex.
     */
    public Vertex<F> getV1() {
        return v1;
    }

    /**
     * Returns the end vertex of the edge.
     *
     * @return The end vertex.
     */
    public Vertex<F> getV2() {
        return v2;
    }

    /**
     * Indicates whether some other edge is "equal to" this one, based on the start and end vertices.
     *
     * @param other The reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Edge)) return false;
        Edge<?> edge = (Edge<?>) other;
        return v1.equals(edge.getV1()) && v2.equals(edge.getV2());
    }

    /**
     * Returns a hash code value for the edge.
     *
     * @return A hash code value for this edge.
     */
    @Override
    public int hashCode() {
        return 31 * v1.hashCode() + v2.hashCode();
    }

    /**
     * Compares this edge with another edge based on the start vertices' values.
     *
     * @param other The edge to compare to.
     * @return A negative integer, zero, or a positive integer as this edge's start vertex
     *         is less than, equal to, or greater than the other edge's start vertex.
     */
    @Override
    public int compareTo(Edge<F> other) {
        return v1.compareTo(other.getV1());
    }

    /**
     * Returns a string representation of the edge in the format "(startVertex -> endVertex)".
     *
     * @return A string representation of the edge.
     */
    @Override
    public String toString() {
        return "(" + v1 + " -> " + v2 + ")";
    }
}
