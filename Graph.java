import java.util.Set;

public interface Graph<F extends Comparable<F>> {

	public boolean addEdge(Edge<F> e);
	// add edge to graph if it's vertices are already in graph
	// and edge is not already in graph

	public boolean addVertex(Vertex<F> v);
	// add vertex to graph if no vertex containing the same 
	// value is already in graph

	public boolean deleteEdge(Edge<F> e);
	// delete edge or return false
	// if no such edge exists in graph

	public boolean deleteVertex(Vertex<F> v);
	// delete vertex and all edges incident to vertex
	// or return false if no such vertex exists in graph
	
	public Set<Vertex<F>> vertexSet();
	//return a set of all vertices
	
	public Set<Edge<F>> edgeSet();
	//return a set of all edges

	

}
