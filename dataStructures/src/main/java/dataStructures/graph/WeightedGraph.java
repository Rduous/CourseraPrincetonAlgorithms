package dataStructures.graph;

import java.util.Collection;
import java.util.Objects;

public interface WeightedGraph<T> {
	
	Collection<Edge<T>> getEdges(T v);
	void addEdge(T v, T w, double weight);
	int numVertices();
	int numEdges();
	Vertex<T> get(T v);
	
	public static class Edge<T> {
		Vertex<T> nextV;
		int weight;
		public Edge(Vertex<T> nextV, int weight) {
			this.nextV = nextV;
			this.weight = weight;
		}
		public Vertex<T> getVertex() {
			return nextV;
		}
		public int getWeight() {
			return weight;
		}
	}
	
	public static class Vertex<T> {
		
		T value;
		double distance = Double.MAX_VALUE;
		public Vertex<T> previous = null;
		
		public Vertex(T value) {
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
				Vertex<?> other = (Vertex<?>) obj;
				return Objects.equals(value, other.value);
			}
			return false;
		}
	}

}
