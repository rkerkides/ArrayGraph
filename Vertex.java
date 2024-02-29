public class Vertex<F> {
    private F value; // The value stored in this vertex

    public Vertex(F value) {
        this.value = value;
    }

    // Accessor for the vertex value
    public F getValue() {
        return value;
    }

    // Setter for the vertex value
    public void setValue(F value) {
        this.value = value;
    }

    // Checks if two vertices are equal based on their values.
    public boolean equals(Object o) {
        if (o instanceof Vertex) {
            Vertex<F> v = (Vertex<F>) o;
            return value.equals(v.getValue());
        }
        return false;
    }

    // Generates a hash code based on the vertex's value.
    public int hashCode() {
        return value.hashCode();
    }

    // Returns a string representation of the vertex.
    public String toString() {
        return value.toString();
    }
}