package dataStructures.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraph<T> implements WeightedGraph<T> {
	
	private final Map<Vertex<T>, List<Edge<T>>> adjacencyLists;
	private final Map<T, Vertex<T>> vertexMap;
	private int numEdges = 0;
	
	public AdjacencyListGraph(Collection<T> vertices) {
		adjacencyLists = new HashMap<Vertex<T>, List<Edge<T>>>(vertices.size());
		vertexMap = new HashMap<T, Vertex<T>>();
		for (T t : vertices) {
			adjacencyLists.put(new Vertex<T>(t), new ArrayList<Edge<T>>());
			vertexMap.put(t, new Vertex<T>(t));
		}
	}

	public Collection<Edge<T>> getEdges(T v) {
		return new ArrayList<Edge<T>>(adjacencyLists.get(v));
	}

	public void addEdge(T v, T w, int weight) {
		adjacencyLists.get(v).add(new Edge<T>(vertexMap.get(w), weight));
		adjacencyLists.get(w).add(new Edge<T>(vertexMap.get(v), weight));
		numEdges++;
	}

	public int numVertices() {
		return adjacencyLists.size();
	}

	public int numEdges() {
		return numEdges;
	}

	public Vertex<T> get(T v) {
		return vertexMap.get(v);
	}

}
