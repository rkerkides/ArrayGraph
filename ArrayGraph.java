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
        if (numEdges >= edges.length) return false;

        // Check if edge already exists
        int edgeIndex = findEdgeIndex(e);
        if (edgeIndex >= 0) return false; // Edge already exists

        // Check for the existence of vertices
        if (findVertexIndex(e.getV1()) < 0 || findVertexIndex(e.getV2()) < 0) return false; // One of the vertices does not exist

        // Convert to insertion point
        int insertionPoint = -edgeIndex - 1;

        // Edge does not exist, proceed to insert it
        insertElementInSortedOrder(edges, e, numEdges++, insertionPoint);
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
        if (numVertices >= vertices.length) return false;

        int index = findVertexIndex(v);
        if (index >= 0) return false; // Vertex already exists, index is the position

        // Convert to insertion point
        int insertionPoint = -index - 1;

        // Vertex does not exist, proceed to insert it
        insertElementInSortedOrder(vertices, v, numVertices++, insertionPoint);

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
        int index = Arrays.binarySearch(edges, 0, numEdges, e);
        if (index >= 0) {
            System.arraycopy(edges, index + 1, edges, index, numEdges - index - 1);
            edges[--numEdges] = null;
            return true;
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
        int index = Arrays.binarySearch(vertices, 0, numVertices, v);
        if (index >= 0) {
            // Remove the vertex by shifting elements
            System.arraycopy(vertices, index + 1, vertices, index, numVertices - index - 1);
            vertices[--numVertices] = null;

            // Instead of deleting edges connected to the vertex one by one,
            // mark them and then compact the array in one pass.
            int compactIndex = 0; // Index for where to place the next unmarked edge
            for (int i = 0; i < numEdges; i++) {
                if (!edges[i].getV1().equals(v) && !edges[i].getV2().equals(v)) {
                    // If the edge is not connected to the deleted vertex, retain it
                    if (compactIndex != i) {
                        edges[compactIndex] = edges[i];
                    }
                    compactIndex++;
                } // Edges connected to the deleted vertex are implicitly discarded
            }

            // Fill the "empty" part of the array with nulls
            for (int i = compactIndex; i < numEdges; i++) {
                edges[i] = null;
            }

            numEdges = compactIndex; // Update the number of edges
            return true;
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
     * Searches for a vertex in the graph and returns it if found, null otherwise.
     *
     * @param v the vertex to find
     * @return the found vertex, or null if not found
     */
    private int findVertexIndex(Vertex<F> v) {
        return Arrays.binarySearch(vertices, 0, numVertices, v);
    }

    /**
     * Searches for an edge in the graph and returns it if found, null otherwise.
     *
     * @param e the edge to find
     * @return the found edge, or null if not found
     */
    private int findEdgeIndex(Edge<F> e) {
        return Arrays.binarySearch(edges, 0, numEdges, e);
    }

    /**
     * Inserts a given element (vertex or edge) into the appropriate sorted array (vertices or edges)
     * at a specified insertion point. This method is integral to maintaining the sorted state of vertices and edges
     * in the graph, facilitating efficient data management and retrieval. It is called after an insertion point
     * has been determined, ensuring the element is added in the correct order without disrupting
     * the sorted integrity of the array.
     *
     * @param <T> the type of elements in the array, which must implement the Comparable interface
     * @param array the sorted array into which the element will be inserted
     * @param element the element to insert into the array
     * @param count the current number of elements in the array, used to limit the search and shift scope
     * @param insertionPoint the calculated position in the array where the new element should be inserted
     */
    private <T> void insertElementInSortedOrder(T[] array, T element, int count, int insertionPoint) {
        System.arraycopy(array, insertionPoint, array, insertionPoint + 1, count - insertionPoint);
        array[insertionPoint] = element; // Insert the new element at the calculated position
    }
}
