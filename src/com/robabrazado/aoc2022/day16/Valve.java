package com.robabrazado.aoc2022.day16;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Valve {
	private final String name;
	private final int flowRate;
	private final List<Valve> neighbors = new ArrayList<Valve>();
	private Map<Valve, Integer> distanceMap = null;
	
	public Valve(String name, int flowRate) {
		this.name = name;
		this.flowRate = flowRate;
		return;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getFlowRate() {
		return this.flowRate;
	}
	
	public Valve[] getNeighbors() {
		 return this.neighbors.toArray(new Valve[this.neighbors.size()]);
	}
	
	public int getDistance(Valve to) {
		if (this.distanceMap == null) {
			this.populateDistances();
		}
		return this.distanceMap.get(to).intValue();
	}
	
	void addNeighbor(Valve v) {
		this.neighbors.add(v);
		return;
	}
	
	private void populateDistances() {
		List<Valve> unseen = this.listAllNodes(); // There's got to be a better way to do this, right?
		Map<Valve, Integer> distances = new HashMap<Valve, Integer>();
		for (Valve v : unseen) {
			distances.put(v, Integer.MAX_VALUE);
		}
		distances.put(this, 0);
		while (!unseen.isEmpty()) {
			Collections.sort(unseen, (Valve a, Valve b) -> distances.get(a) - distances.get(b));
			Valve curr = unseen.get(0);
			unseen.remove(curr);
			Valve[] neighbors = curr.getNeighbors();
			for (Valve neighbor : neighbors) {
				int newDistance = distances.get(curr) + 1;
				if (distances.get(neighbor) > newDistance) {
					distances.put(neighbor, newDistance);
				}
			}
		}
		this.distanceMap = distances;
		return;
	}
	
	private List<Valve> listAllNodes() {
		Set<Valve> all = new HashSet<Valve>();
		collectAllNodes(this, all);
		return new ArrayList<Valve>(all);
	}
	
	private static void collectAllNodes(Valve node, Set<Valve> valves) {
		valves.add(node);
		Valve[] neighbors = node.getNeighbors();
		for (Valve neighbor : neighbors) {
			if (!valves.contains(neighbor)) {
				collectAllNodes(neighbor, valves);
			}
		}
		return;
	}
}
