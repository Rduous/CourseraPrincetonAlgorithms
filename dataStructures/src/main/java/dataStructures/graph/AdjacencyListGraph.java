package dataStructures.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraph implements WeightedGraph {
	
	private final Map<Vertex, List<Edge>> adjacencyLists;
	private final Map<Integer, Vertex> vertexMap;
	private int numEdges = 0;
	
	public AdjacencyListGraph(int numVertices) {
		adjacencyLists = new HashMap<Vertex, List<Edge>>(numVertices);
		vertexMap = new HashMap<Integer, WeightedGraph.Vertex>();
		for (int i = 0; i < numVertices; i++) {
			adjacencyLists.put(new Vertex(i), new ArrayList<Edge>());
			vertexMap.put(i, new Vertex(i));
		}
	}

	public Collection<Edge> getEdges(int v) {
		return new ArrayList<WeightedGraph.Edge>(adjacencyLists.get(v));
	}

	public void addEdge(int v, int w, int weight) {
		adjacencyLists.get(v).add(new Edge(vertexMap.get(w), weight));
		adjacencyLists.get(w).add(new Edge(vertexMap.get(v), weight));
		numEdges++;
	}

	public int numVertices() {
		return adjacencyLists.size();
	}

	public int numEdges() {
		return numEdges;
	}

	@Override
	public Vertex get(int v) {
		return vertexMap.get(v);
	}

}
