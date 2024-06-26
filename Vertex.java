/**
 * Represents a vertex in a graph with a label of a generic type F.
 * The generic type F must be Comparable to ensure that vertices can be sorted.
 * This class provides methods to access and modify the vertex's value,
 * and it overrides equals, hashCode, and toString methods for proper functionality
 * within collections and other data structures.
 *
 * @param <F> the type of the label for the vertex, which must be Comparable
 *
 * @author Renos Kerkides
 * @studentNumber 2923219K
 */
public class Vertex<F extends Comparable<F>> implements Comparable<Vertex<F>> {
    private final F value; // The value stored in this vertex

    /**
     * Constructs a Vertex with the specified value.
     *
     * @param value The value to be stored in the vertex.
     */
    public Vertex(F value) {
        this.value = value;
    }

    /**
     * Returns the value stored in the vertex.
     *
     * @return The value of the vertex.
     */
    public F getValue() {
        return value;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * The equality is based on the value of the vertices.
     *
     * @param other The reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Vertex)) return false;
        Vertex<?> vertex = (Vertex<?>) other;
        return value.equals(vertex.value);
    }

    /**
     * Returns a hash code value for the vertex.
     * The hash code is generated based on the vertex's value.
     *
     * @return a hash code value for this vertex.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Compares this vertex with another vertex for order.
     * Returns a negative integer, zero, or a positive integer as this vertex's value
     * is less than, equal to, or greater than the specified vertex's value.
     *
     * @param other The vertex to be compared.
     * @return a negative integer, zero, or a positive integer as this vertex
     *         is less than, equal to, or greater than the specified vertex.
     */
    @Override
    public int compareTo(Vertex<F> other) {
        return this.value.compareTo(other.value);
    }

    /**
     * Returns a string representation of the vertex.
     * This implementation returns the string representation of the vertex's value.
     *
     * @return a string representation of the vertex.
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
