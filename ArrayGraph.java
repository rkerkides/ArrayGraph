import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Implements a graph abstract data type (ADT) using arrays to store vertices and edges.
 * This class enforces that no duplicate vertices can be added, edges can only exist between
 * added vertices, and maintains vertices and edges sorted in ascending order.
 *
 * @param <F> the type of the labels for vertices in the graph, which must be comparable
 *
 * @author Renos Kerkides
 * @studentNumber 2923219K
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
     * Removes a specified vertex from the graph, including all edges connected to it, thereby maintaining the graph's structural integrity.
     * The method locates the vertex using binary search. Upon finding the vertex, it is removed by shifting subsequent elements in the vertex array
     * to the left, preserving the sorted order. Subsequently, the method iterates through the edge array, efficiently removing any edges connected
     * to the deleted vertex while keeping unconnected edges intact. This streamlined removal process ensures the graph remains accurate and consistent.
     *
     * @param v the vertex to be deleted from the graph
     * @return true if the vertex was found and successfully removed, false otherwise, indicating the vertex does not exist in the graph
     */
    @Override
    public boolean deleteVertex(Vertex<F> v) {
        int index = findVertexIndex(v);
        if (index >= 0) {
            System.arraycopy(vertices, index + 1, vertices, index, numVertices - index - 1);
            vertices[--numVertices] = null;

            int compactIndex = 0; // Index for the next unmarked edge
            for (int i = 0; i < numEdges; i++) {
                if (!edges[i].getV1().equals(v) && !edges[i].getV2().equals(v)) {
                    if (compactIndex != i) {
                        edges[compactIndex] = edges[i];
                    }
                    compactIndex++;
                } // Implicit discard of edges connected to the vertex
            }

            for (int i = compactIndex; i < numEdges; i++) {
                edges[i] = null;
            }

            numEdges = compactIndex; // Update the count of edges
            return true;
        }
        return false;
    }

    /**
     * Deletes an existing edge from the graph, ensuring the graph's integrity by updating the edge array accordingly.
     * This operation involves a binary search to locate the target edge within the edges array. If the edge is found,
     * it is removed by shifting subsequent elements in the array to the left, effectively filling the gap left by the deleted edge.
     * This maintains the edges array in a compact and sorted state after the deletion, and the edge count is updated to reflect the removal.
     *
     * @param e the edge to be deleted from the graph
     * @return true if the edge was found and successfully removed, false otherwise, indicating the edge does not exist in the graph
     */
    @Override
    public boolean deleteEdge(Edge<F> e) {
        int index = findEdgeIndex(e);
        if (index >= 0) {
            System.arraycopy(edges, index + 1, edges, index, numEdges - index - 1);
            edges[--numEdges] = null;
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
        // Return a LinkedHashSet to maintain order
        return new LinkedHashSet<>(Arrays.asList(vertices).subList(0, numVertices));
    }

    /**
     * Returns a set containing all the edges in the graph.
     *
     * @return a Set of all edges
     */
    @Override
    public Set<Edge<F>> edgeSet() {
        // Return a LinkedHashSet to maintain order
        return new LinkedHashSet<>(Arrays.asList(edges).subList(0, numEdges));
    }

    /**
     * Searches for the index of a specified vertex within the array of vertices.
     *
     * @param v the vertex to find
     * @return the index of the vertex if found within the array of vertices; otherwise, returns a negative value.
     *         This result can then be converted into an appropriate insertion point for the vertex
     *         by negating and decrementing the returned value:
     *         insertion point = - (returnedValue) - 1
     */
    private int findVertexIndex(Vertex<F> v) {
        return Arrays.binarySearch(vertices, 0, numVertices, v);
    }

    /**
     * Searches for the index of a specified edge within the array of edges using binary search.
     *
     * @param e the edge to find
     * @return the index of the edge if found within the array of edge; otherwise, returns a negative value.
     *         This result can then be converted into an appropriate insertion point for the vertex
     *         by negating and decrementing the returned value:
     *         insertion point = - (returnedValue) - 1
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
