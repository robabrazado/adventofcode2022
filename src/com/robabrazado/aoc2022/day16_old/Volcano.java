package com.robabrazado.aoc2022.day16_old;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Volcano {
	private final Map<String, Valve> valveMap = new HashMap<String, Valve>();
	private final List<Valve> valvesToOpen = new ArrayList<Valve>();
	private final Map<Valve, Map<Valve, Integer>> distances = new HashMap<Valve, Map<Valve, Integer>>();
	
	private Valve currentPosition;
	private int minutesRemaining;
	private int pressureReleased = 0;
	
	public Volcano(String[] inputLines, int eruptsIn) {
		this.currentPosition = this.parseInput(inputLines);
		this.minutesRemaining = eruptsIn;
		return;
	}
	
	public int part1() {
		int minutesRemaining = 30;
		return valueOf(this.currentPosition, minutesRemaining);
	}
	
	public int part1_busted() {
		boolean keepLooking = true;
		while (keepLooking) {
			// Find highest value destination reachable in time
			List<Valve> possibleDestinations = this.valveMap.values().stream().filter(
				(Valve v) -> this.distanceTo(v) < this.minutesRemaining).collect(Collectors.toList());
			Collections.sort(possibleDestinations,
				(Valve a, Valve b) -> (b.getPressureReleasedAfter(this.minutesRemaining - this.distanceTo(b) - 1) - a.getPressureReleasedAfter(this.minutesRemaining - this.distanceTo(a) - 1)));
			
			if (possibleDestinations.size() > 0) {
				Valve destination = possibleDestinations.get(0);
				// Compute time and pressure
				this.minutesRemaining -= (this.distanceTo(destination) + 1);
				this.pressureReleased += destination.getFlowRate() * this.minutesRemaining;
				
				// Move to destination and open the valve
				this.currentPosition = destination;
				destination.open();
				this.valvesToOpen.remove(destination);
				
			} else {
				// We're either out of valves or out of time
				keepLooking = false;
			}
		}
		return this.pressureReleased;
	}
	
	private int distanceTo(Valve dest) {
		if (!this.distances.containsKey(this.currentPosition)) {
			Set<Valve> unseen = new HashSet<Valve>();
			Map<Valve, Integer> dists = new HashMap<Valve, Integer>();
			for (Valve v : this.valveMap.values()) {
				dists.put(v, Integer.MAX_VALUE);
				unseen.add(v);
			}
			dists.put(this.currentPosition, 0);
			
			while (!unseen.isEmpty()) {
				List<Valve> starts = new ArrayList<Valve>(unseen);
				Collections.sort(starts, (Valve a, Valve b) -> dists.get(a) - dists.get(b));
				Valve curr = starts.get(0);
				unseen.remove(curr);
				for (Valve neighbor : curr.getNeighbors()) {
					if (unseen.contains(neighbor)) {
						int newDist = dists.get(curr) + 1;
						if (dists.get(neighbor) > newDist) {
							dists.put(neighbor, newDist);
						}
					}
				}
			}
			this.distances.put(this.currentPosition, dists);
		}
		return this.distances.get(this.currentPosition).get(dest).intValue();
	}
	
	private int valueOf(Valve v, int minutesRemaining) {
		return this.valueOf(v, minutesRemaining, new ArrayList<Valve>());
	}
	
	private int valueOf(Valve v, int minutesRemaining, Collection<Valve> alreadyOpen) {
		int value = 0;
		int bestNeighborValue = 0;
		if (minutesRemaining > 0) {
			Valve[] neighbors = v.getNeighbors();
			// First check for best neighbor value assuming we don't open self
			for (Valve n : neighbors) {
				bestNeighborValue = Math.max(bestNeighborValue, this.valueOf(n, minutesRemaining - 1, alreadyOpen));
			}
			value = bestNeighborValue;
			// If opening self is an option (and worthwhile), check for new neighbor values
			if (!alreadyOpen.contains(v) && v.getFlowRate() > 0 && minutesRemaining > 2) {
				alreadyOpen.add(v);
				for (Valve n : neighbors) {
					bestNeighborValue = Math.max(bestNeighborValue, this.valueOf(n, minutesRemaining - 2, alreadyOpen));
				}
				value = Math.max(value, (v.getFlowRate() * (minutesRemaining - 1)) + bestNeighborValue);
			}
		}
		
		return value;
	}
	
	// Returns starting point; i.e. first valve in input list
	private Valve parseInput(String[] inputLines) {
		int numValves = inputLines.length;
		Valve[] valveArr = new Valve[numValves];
		String[][] neighborLists = new String[numValves][];
		
		for (int i = 0; i < numValves; i++) {
			String[] parts = inputLines[i].split("Valve | has flow rate=|; tunnels?? leads?? to valves?? ");
			String vName = parts[1];
			Valve v = new Valve(vName, Integer.parseInt(parts[2]));
			valveArr[i] = v;
			this.valveMap.put(vName, v);
			if (v.getFlowRate() > 0) {
				this.valvesToOpen.add(v);
			}
			neighborLists[i] = parts[3].split(", ");
		}
		
		for (int i = 0; i < numValves; i++) {
			for (String name : neighborLists[i]) {
				valveArr[i].addNeighbor(this.valveMap.get(name));
			}
		}
		
		return valveArr[0];
	}
	
}
