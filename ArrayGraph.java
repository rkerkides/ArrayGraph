import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Implements a graph abstract data type (ADT) using arrays to store vertices and edges.
 * This class enforces that no duplicate vertices can be added, edges can only exist between
 * added vertices, and maintains vertices and edges sorted in ascending order.
 *
 * @param <F> the type of the labels for vertices in the graph, which must be comparable
 */
@SuppressWarnings("unchecked")
public class ArrayGraph<F extends Comparable<F>> implements Graph<F> {
    private final Vertex<F>[] vertices;
    private final Edge<F>[] edges;
    private int numVertices = 0;
    private int numEdges = 0;

    /**
     * Constructs an ArrayGraph with predefined capacities for vertices and edges.
     */
    public ArrayGraph() {
        vertices = (Vertex<F>[]) new Vertex<?>[20]; // Maximum of 20 vertices
        edges = (Edge<F>[]) new Edge<?>[50]; // Maximum of 50 edges
    }

    /**
     * Adds an edge to the graph if it doesn't already exist and both vertices of the edge are present.
     *
     * @param e the edge to add
     * @return true if the edge was added, false otherwise
     */
    @Override
    public boolean addEdge(Edge<F> e) {
        // Check if adding the edge is possible (not exceeding capacity, vertices exist, edge doesn't exist)
        if (numEdges >= edges.length || containsEdge(e) || !containsVertex(e.getV1()) || !containsVertex(e.getV2()))
            return false;

        // Insert the edge in sorted order, sorting edges by the value of the first vertex
        insertSorted(edges, e, numEdges++);
        return true;
    }

    /**
     * Adds a vertex to the graph if it doesn't already exist.
     *
     * @param v the vertex to add
     * @return true if the vertex was added, false otherwise
     */
    @Override
    public boolean addVertex(Vertex<F> v) {
        // Check if adding the vertex is possible (not exceeding capacity, vertex doesn't exist)
        if (numVertices >= vertices.length || containsVertex(v))
            return false;

        // Insert the vertex in sorted order
        insertSorted(vertices, v, numVertices++);
        return true;
    }

    /**
     * Deletes an edge from the graph if it exists.
     *
     * @param e the edge to delete
     * @return true if the edge was deleted, false otherwise
     */
    @Override
    public boolean deleteEdge(Edge<F> e) {
        // Find and remove the edge, then shift remaining edges to fill the gap
        for (int i = 0; i < numEdges; i++) {
            if (edges[i].equals(e)) {
                System.arraycopy(edges, i + 1, edges, i, numEdges - i - 1);
                edges[--numEdges] = null; // Setting to null to help with garbage collection
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a vertex from the graph if it exists, along with any edges connected to it.
     *
     * @param v the vertex to delete
     * @return true if the vertex was deleted, false otherwise
     */
    @Override
    public boolean deleteVertex(Vertex<F> v) {
        // Find and remove the vertex, then shift remaining vertices to fill the gap
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(v)) {
                System.arraycopy(vertices, i + 1, vertices, i, numVertices - i - 1);
                vertices[--numVertices] = null; // Setting to null to help with garbage collection

                // Delete all edges connected to the vertex
                for (int j = numEdges - 1; j >= 0; j--) {
                    if (edges[j].getV1().equals(v) || edges[j].getV2().equals(v)) {
                        deleteEdge(edges[j]);
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a set containing all the vertices in the graph.
     *
     * @return a Set of all vertices
     */
    @Override
    public Set<Vertex<F>> vertexSet() {
        return new HashSet<>(Arrays.asList(vertices).subList(0, numVertices));
    }

    /**
     * Returns a set containing all the edges in the graph.
     *
     * @return a Set of all edges
     */
    @Override
    public Set<Edge<F>> edgeSet() {
        return new HashSet<>(Arrays.asList(edges).subList(0, numEdges));
    }

    /**
     * Checks if a vertex exists in the graph.
     *
     * @param v the vertex to check
     * @return true if the vertex exists, false otherwise
     */
    private boolean containsVertex(Vertex<F> v) {
        // Iterate over vertices to check for existence
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(v)) return true;
        }
        return false;
    }

    /**
     * Checks if an edge exists in the graph.
     *
     * @param e the edge to check
     * @return true if the edge exists, false otherwise
     */
    private boolean containsEdge(Edge<F> e) {
        // Iterate over edges to check for existence
        for (int i = 0; i < numEdges; i++) {
            if (edges[i].equals(e)) return true;
        }
        return false;
    }

    /**
     * Inserts an element into a sorted array in its correct position to maintain the sorted order.
     * This method uses binary search to find the insertion point and shifts elements to the right
     * to make space for the new element.
     *
     * @param <T> the type of elements in the array, which must be comparable based on the provided comparator
     * @param array the sorted array into which the element will be inserted
     * @param element the element to insert into the array
     * @param count the current number of elements in the array
     */
    private <T> void insertSorted(T[] array, T element, int count) {
        int insertionPoint = Arrays.binarySearch(array, 0, count, element);
        if (insertionPoint < 0) {
            insertionPoint = -(insertionPoint + 1); // Convert insertion point to positive index
        }

        // Shift elements to the right to make space for the new element
        System.arraycopy(array, insertionPoint, array, insertionPoint + 1, count - insertionPoint);
        array[insertionPoint] = element; // Insert the new element
    }
}
