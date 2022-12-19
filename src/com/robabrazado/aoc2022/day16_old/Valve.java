package com.robabrazado.aoc2022.day16_old;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Valve {
	private final String name;
	private final int flowRate;
	private final Map<String, Valve> neighbors = new HashMap<String, Valve>();
	
	private boolean isOpen = false;
	
	private Valve[] neighborsCache = null;
	
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
	
	public boolean isOpen() {
		return this.isOpen;
	}
	
	// Returns flowRate;
	public int open() {
		this.isOpen = true;
		return this.flowRate;
	}
	
	public int getPressureReleasedAfter(int minutes) {
		return this.flowRate * minutes;
	}
	
	public Valve[] getNeighbors() {
		 if (neighborsCache == null) {
			 this.neighborsCache = (new ArrayList<Valve>(this.neighbors.values())).toArray(new Valve[this.neighbors.size()]);
		 }
		 return Arrays.copyOf(this.neighborsCache, this.neighborsCache.length);
	}
	
	public void addNeighbor(Valve v) {
		this.neighbors.put(v.getName(), v);
		return;
	}
	
}
