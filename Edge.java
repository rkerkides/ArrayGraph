public class Edge<F extends Comparable<F>> {
    private Vertex<F> v1; // Start vertex, label is smaller
    private Vertex<F> v2; // End vertex, label is larger

    public Edge(Vertex<F> v1, Vertex<F> v2) {
        // Automatically order vertices to enforce the rule that v1 < v2
        if (v1.getValue().compareTo(v2.getValue()) < 0) {
            this.v1 = v1;
            this.v2 = v2;
        } else {
            this.v1 = v2;
            this.v2 = v1;
        }
    }

    // Accessor for the start vertex
    public Vertex<F> getV1() {
        return v1;
    }

    // Accessor for the end vertex
    public Vertex<F> getV2() {
        return v2;
    }

    // Edges are equal if they have the same start and end vertices
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge<?> edge = (Edge<?>) o;
        return v1.equals(edge.getV1()) && v2.equals(edge.getV2());
    }

    // Hash code method reflecting directed nature
    // Multiply the current hash code by 31, a prime number,
    // before adding the hash code of the next field.
    // Using a prime number like 31 helps in distributing the hash codes
    // more evenly across the hash tables.
    // This reduces the chance of collisions
    // (where different objects have the same hash code).
    public int hashCode() {
        return 31 * v1.hashCode() + v2.hashCode();
    }

    // String representation
    public String toString() {
        return "(" + v1 + " -> " + v2 + ")"; // For directed graph
    }
}
