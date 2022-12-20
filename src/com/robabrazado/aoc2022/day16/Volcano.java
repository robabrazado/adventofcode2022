package com.robabrazado.aoc2022.day16;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Volcano {
	private final Valve[] allValves;
	private final Valve startValve;
	
	public Volcano(String[] inputLines) {
		this.allValves = this.parseInput(inputLines);
		Valve tmpStart = null;
		for (int i = 0; i < this.allValves.length && tmpStart == null; i++) {
			if (this.allValves[i].getName().equals("AA")) {
				tmpStart = this.allValves[i];
			}
		}
		if (tmpStart == null) {
			throw new IllegalArgumentException("No start valve found");
		}
		
		this.startValve = tmpStart;
		return;
	}
	
	public int part1(int eruptsIn) {
		// Gather a collection of all valves with positive flow rate
		Set<Valve> valvesOfInterest = new HashSet<Valve>();
		for (Valve v : this.allValves) {
			if (v.getFlowRate() > 0) {
				valvesOfInterest.add(v);
			}
		}
		
		int maxPressure = 0;
		Iterator<Valve[]> it = new RouteGenerator(this.startValve, valvesOfInterest, eruptsIn);
		while (it.hasNext()) {
			Valve[] route = it.next();
			int newPressure = getRoutePressure(this.startValve, route, eruptsIn);
			maxPressure = Math.max(maxPressure, newPressure);
		}
		
		return maxPressure;
	}
	
	public BestRouteFinder routeFinder(int timeLimit, String startName) {
		return new BestRouteFinder(Arrays.asList(this.allValves), timeLimit, startName);
	}
	
	// Assumes opening each destination valve
	private static int getRoutePressure(Valve start, Valve[] route, int maxMinutes) {
		int pressure = 0;
		int minutesLeft = maxMinutes;
		Queue<Valve> routeQ = new ArrayDeque<Valve>(Arrays.asList(route));
		Valve prev = start;
		while (!routeQ.isEmpty() && minutesLeft > 1) {
			Valve curr = routeQ.poll();
			minutesLeft -= prev.getDistance(curr) + 1;
			if (minutesLeft >= 0) {
				pressure += minutesLeft * curr.getFlowRate();
			}
			prev = curr;
		}
		return pressure;
	}
	
	private Valve[] parseInput(String[] inputLines) {
		int numValves = inputLines.length;
		Map<String, Valve> valveMap = new HashMap<String, Valve>();
		Valve[] valveArr = new Valve[numValves];
		String[][] neighborLists = new String[numValves][];
		
		for (int i = 0; i < numValves; i++) {
			String[] parts = inputLines[i].split("Valve | has flow rate=|; tunnels?? leads?? to valves?? ");
			String vName = parts[1];
			Valve v = new Valve(vName, Integer.parseInt(parts[2]));
			valveArr[i] = v;
			valveMap.put(vName, v);
			neighborLists[i] = parts[3].split(", ");
		}
		
		for (int i = 0; i < numValves; i++) {
			for (String name : neighborLists[i]) {
				valveArr[i].addNeighbor(valveMap.get(name));
			}
		}
		
		return valveArr;
	}
	
}
