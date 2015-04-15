package dataStructures.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Dijkstra2 {

	public static <T> List<T> shortestPath(Map<Vertex<T>, List<Edge<T>>> adjacencies, Vertex<T> initial, Vertex<T> goal) {

		PriorityQueue<Vertex<T>> pq = new PriorityQueue<Vertex<T>>();
		pq.add(initial);
		Set<Vertex<T>> visited = new HashSet<Vertex<T>>();
		initial.distance = 0;

		Vertex<T> current = null;
		while (!pq.isEmpty() && !goal.equals(current)) {
			current = pq.poll();
			double prevDistance = current.distance;
			for (Edge<T> e : adjacencies.get(current)) {
				Vertex<T> v = e.next;
				if (!visited.contains(v)) {
					double distance = prevDistance + e.weight;
					if (distance < v.distance) {
						decreaseDistance(v, distance, pq, current);
					}
				}
			}
			visited.add(current);
		}

		List<T> shortestPath = collectPath(goal);
		return shortestPath;
	}

	private static <T> void decreaseDistance(Vertex<T> v, double distance, PriorityQueue<Vertex<T>> pq,
			Vertex<T> current) {
		pq.remove(v);
		v.distance = distance;
		v.previous = current;
		pq.add(v);
	}

	private static <T> List<T> collectPath(Vertex<T> goal) {
		List<T> shortestPath = new LinkedList<T>();
		Vertex<T> cur = goal;
		while (cur != null) {
			shortestPath.add(0, cur.value);
			cur = cur.previous;
		}
		return shortestPath;
	}

	private static class Edge<T> {
		Vertex<T> next;
		double weight;
		public Edge(Vertex<T> next, double weight) {
			this.next = next;
			this.weight = weight;
		}
	}

	private static class Vertex<T> implements Comparable<Vertex<T>> {

		T value;
		double distance = Double.POSITIVE_INFINITY;
		Vertex<T> previous = null;

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (obj instanceof Vertex) {
				Vertex<?> other = (Vertex<?>) obj;
				return Objects.equals(value, other.value);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hash(value);
		}

		@Override
		public int compareTo(Vertex<T> o) {
			return Double.compare(distance, o.distance);
		}
	}

	public static void main(String[] args) {
		Map<Vertex<Integer>, List<Edge<Integer>>> adjacencies = new HashMap<Vertex<Integer>, List<Edge<Integer>>>();
		Map<Integer, Vertex<Integer>> vertexMap = new HashMap<Integer, Vertex<Integer>>();

		for (int i = 1; i <= 7; i++) {
			Vertex<Integer> v = new Vertex<Integer>();
			v.value = i;
			vertexMap.put(i, v);
			adjacencies.put(v, new ArrayList<Edge<Integer>>());
		}

		int[][] edges = { { 1, 2, 4 }, { 1, 3, 3 }, { 1, 5, 7 }, { 2, 3, 6 }, { 2, 4, 5 }, { 3, 4, 11 }, { 3, 5, 8 },
				{ 4, 5, 2 }, { 4, 7, 10 }, { 4, 6, 2 }, { 5, 7, 5 }, { 6, 7, 3 } };
		
		for (int[] edge : edges) {
			Vertex<Integer> u = vertexMap.get(edge[0]);
			Vertex<Integer> v = vertexMap.get(edge[1]);
			int w = edge[2];
			adjacencies.get(u).add(new Edge<Integer>(v, w));
			adjacencies.get(v).add(new Edge<Integer>(u, w));
		}

		List<Integer> path = shortestPath(adjacencies, vertexMap.get(1), vertexMap.get(6));
		System.out.println(Arrays.toString(path.toArray()));
	}

}
