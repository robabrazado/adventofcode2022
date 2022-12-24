package com.robabrazado.aoc2022.day24;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Location {
	private static final Map<Integer, Map<Integer, Location>> LOCATIONS = new HashMap<Integer, Map<Integer, Location>>();
	
	private final int x;
	private final int y;
	private final Map<Direction, Location> neighbors = new HashMap<Direction, Location>();
	
	private Location[] neighborCache = null;
	private Integer hashCodeCache = null;
	
	private Location(int x, int y) {
		this.x = x;
		this.y = y;
		return;
	}
	
	public int x() {
		return this.x;
	}
	
	public int y() {
		return this.y;
	}
	
	public Location getNeighbor(Direction dir) {
		Location retVal = this.neighbors.get(dir);
		if (retVal == null) {
			retVal = Location.getLocation(this.x + dir.xOffset(), this.y + dir.yOffset());
			this.neighbors.put(dir, retVal);
		}
		return retVal;
	}
	
	// Array index corresponds to Direction ordinal
	public Location[] getAllNeighbors() {
		if (this.neighborCache == null) {
			this.neighborCache = new Location[Direction.values().length];
			for (Direction d : Direction.values()) {
				this.neighborCache[d.ordinal()] = this.getNeighbor(d);
			}
		}
		return Arrays.copyOf(this.neighborCache, this.neighborCache.length);
	}
	
	public static Location getLocation(int x, int y) {
		Map<Integer, Location> yMap = LOCATIONS.get(x);
		if (yMap == null) {
			yMap = new HashMap<Integer, Location>();
			LOCATIONS.put(x, yMap);
		}
		Location retVal = yMap.get(y);
		if (retVal == null) {
			retVal = new Location(x, y);
			yMap.put(y, retVal);
		}
		return retVal;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equal = false;
		
		if (o instanceof Location) {
			Location other = (Location) o;
			equal = this.x == other.x && this.y == other.y;
		}
		
		return equal;
	}
	
	@Override
	public int hashCode() {
		if (this.hashCodeCache == null) {
			this.hashCodeCache = Integer.valueOf(this.x ^ this.y);
		}
		return this.hashCodeCache.intValue();
	}
}
