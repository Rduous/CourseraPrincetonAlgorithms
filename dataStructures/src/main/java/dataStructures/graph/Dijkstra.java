package dataStructures.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import dataStructures.graph.WeightedGraph.Edge;
import dataStructures.graph.WeightedGraph.Vertex;

public class Dijkstra {

	public static List<Integer> findShortestPath(WeightedGraph<Integer> graph, int initial, int goal) {

		Map<Integer, Integer> distanceMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < graph.numVertices(); i++) {
			distanceMap.put(i, Integer.MAX_VALUE);
		}
		distanceMap.put(initial, 0);

		PriorityQueue<Vertex<Integer>> pq = new PriorityQueue<Vertex<Integer>>();

		Set<Vertex<Integer>> visited = new HashSet<Vertex<Integer>>();

		while (!pq.isEmpty() && ! visited.contains(goal)) {
			Vertex<Integer> current = pq.poll();
			int currentDistance = distanceMap.get(current);

			for (Edge<Integer> e : graph.getEdges(current.value)) {
				if (!visited.contains(e.nextV)) {
					double weight = e.weight;
					double distance = currentDistance + weight;
					if (distance < e.nextV.distance) {
						decreaseDistance(e.nextV, current, distance, pq, distanceMap);
					}
				}
			}
			visited.add(current);
		}

		List<Integer> shortestPath = new LinkedList<Integer>();
		Vertex<Integer> cur = graph.get(goal);
		while (cur != null) {
			shortestPath.add(0, cur.value);
			cur = cur.previous;
		}
		
		return shortestPath;
	}

	private static void decreaseDistance(Vertex<Integer> v, Vertex<Integer> previous, double distance, PriorityQueue<Vertex<Integer>> pq, Map<Integer, Integer> distanceMap) {
		pq.remove(v);
		v.distance = distance;
		v.previous = previous;
		pq.add(v);
	}

}
