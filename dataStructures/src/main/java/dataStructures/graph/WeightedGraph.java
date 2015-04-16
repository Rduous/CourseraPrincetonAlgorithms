package dataStructures.graph;

import java.util.Collection;
import java.util.Objects;

public interface WeightedGraph {
	
	Collection<Edge> getEdges(int v);
	void addEdge(int v, int w, int weight);
	int numVertices();
	int numEdges();
	Vertex get(int v);
	
	public static class Edge {
		Vertex nextV;
		int weight;
		public Edge(Vertex nextV, int weight) {
			this.nextV = nextV;
			this.weight = weight;
		}
		public Vertex getVertex() {
			return nextV;
		}
		public int getWeight() {
			return weight;
		}
	}
	
	public static class Vertex {
		
		int value;
		int distance = Integer.MAX_VALUE;
		public Vertex previous = null;
		
		public Vertex(int value) {
			this.value = value;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(value);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (obj instanceof Vertex) {
				Vertex other = (Vertex) obj;
				return Objects.equals(value, other.value);
			}
			return false;
		}
	}

}