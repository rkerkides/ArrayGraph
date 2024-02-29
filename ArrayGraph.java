import java.util.HashSet;
import java.util.Set;

public class ArrayGraph<F extends Comparable<F>> implements Graph<F> {
    private Vertex<F>[] vertices;
    private Edge<F>[] edges;
    private int numVertices = 0;
    private int numEdges = 0;


    // Suppressing unchecked cast warnings
    @SuppressWarnings("unchecked")
    public ArrayGraph() {
        // Due to type erasure, arrays of generic types cannot be directly instantiated
        // The workaround involves creating an array of a raw type and casting it
        vertices = (Vertex<F>[])new Vertex<?>[20];
        edges = (Edge<F>[])new Edge<?>[50];
    }


    // Adds an edge to the graph if it doesn't already exist and both vertices are present
    @Override
    public boolean addEdge(Edge<F> e) {
        // Check for edge capacity, existence, and presence of its vertices
        if (numEdges == edges.length || containsEdge(e) || !containsVertex(e.getV1())
                || !containsVertex(e.getV2())) return false;
        edges[numEdges++] = e; // Add edge and increment edge count
        sortEdges();
        return true;
    }

    // Adds a vertex to the graph if it doesn't already exist
    @Override
    public boolean addVertex(Vertex<F> v) {
        // Check for vertex capacity and existence
        if (numVertices == vertices.length || containsVertex(v)) return false;
        vertices[numVertices++] = v; // Add vertex and increment vertex count
        sortVertices();
        return true;
    }

    // Deletes an edge from the graph, if it exists
    @Override
    public boolean deleteEdge(Edge<F> e) {
        // Check if the edge exists in the graph; if not, return false.
        if (!containsEdge(e)) return false;

        // Iterate through the edges array to find the matching edge.
        for (int i = 0; i < numEdges; i++) {
            if (edges[i].equals(e)) {
                // Once found, shift the subsequent edges one position to the left
                // to fill the gap created by the removed edge.
                for (int j = i; j < numEdges - 1; j++) {
                    edges[j] = edges[j + 1];
                }
                // Decrement the count of edges to reflect the removal.
                numEdges--;
                return true; // Return true to indicate the edge was successfully deleted.
            }
        }
        return false; // In case the edge was not found (which should not happen due to the initial check), return false.
    }

    // Deletes a vertex from the graph, if it exists, along with any connected edges
    @Override
    public boolean deleteVertex(Vertex<F> v) {
        // Check if the vertex exists in the graph; if not, return false.
        if (!containsVertex(v)) return false;

        // Iterate through the vertices array to find the matching vertex.
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(v)) {
                // Once found, shift the subsequent vertices one position to the left
                // to fill the gap created by the removed vertex.
                for (int j = i; j < numVertices - 1; j++) {
                    vertices[j] = vertices[j + 1];
                }
                // Decrement the count of vertices to reflect the removal.
                numVertices--;

                // Iterate through the edges array to remove any edges connected to the removed vertex.
                // Note: Adjusting the loop index inside the loop to account for the array size change
                // when an edge is deleted.
                for (int j = 0; j < numEdges; j++) {
                    if (edges[j].getV1().equals(v) || edges[j].getV2().equals(v)) {
                        deleteEdge(edges[j]);
                        j--; // Decrement the index to revisit the same position, as elements have shifted.
                    }
                }
                return true; // Return true to indicate the vertex (and any connected edges) was successfully deleted.
            }
        }
        return false; // In case the vertex was not found (which should not happen due to the initial check), return false.
    }

    // Returns a set of all vertices in the graph
    // Consider TreeSet for sorting
    @Override
    public Set<Vertex<F>> vertexSet() {
        Set<Vertex<F>> vertexSet = new HashSet<>();
        for (int i = 0; i < numVertices; i++) {
            vertexSet.add(vertices[i]);
        }
        return vertexSet;
    }


    // Returns a set of all edges in the graph
    @Override
    public Set<Edge<F>> edgeSet() {
        Set<Edge<F>> edgeSet = new HashSet<>();
        for (int i = 0; i < numEdges; i++) {
            edgeSet.add(edges[i]);
        }
        return edgeSet;
    }

    // Helper method to check if a vertex exists in the graph
    private boolean containsVertex(Vertex<F> v) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i].equals(v)) return true;
        }
        return false;
    }

    // Helper method to check if an edge exists in the graph
    private boolean containsEdge(Edge<F> e) {
        for (int i = 0; i < numEdges; i++) {
            if (edges[i].equals(e)) return true;
        }
        return false;
    }

    // Sorts the vertices array to maintain order
    private void sortVertices() {
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (vertices[i].getValue().compareTo(vertices[j].getValue()) > 0) {
                    Vertex<F> temp = vertices[i];
                    vertices[i] = vertices[j];
                    vertices[j] = temp;
                }
            }
        }
    }

    // Sorts the edges array to maintain order
    private void sortEdges() {
        for (int i = 0; i < numEdges; i++) {
            for (int j = i + 1; j < numEdges; j++) {
                if (edges[i].getV1().getValue().compareTo(edges[j].getV1().getValue()) > 0) {
                    Edge<F> temp = edges[i];
                    edges[i] = edges[j];
                    edges[j] = temp;
                }
            }
        }
    }

}
