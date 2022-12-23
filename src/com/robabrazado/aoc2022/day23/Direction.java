package com.robabrazado.aoc2022.day23;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
	NORTH,
	SOUTH,
	WEST,
	EAST;
	
	private static final Direction[] DIRECTIONS = Direction.values();
	private static final int NUM_DIRECTIONS = DIRECTIONS.length;
	private static final Map<Direction, Direction[]> VALUES_FROM_START_CACHE = new HashMap<Direction, Direction[]>();
	
	public Direction next() {
		return DIRECTIONS[(this.ordinal() + 1) % NUM_DIRECTIONS];
	}
	
	public Direction[] valuesStartingWithCurrent() {
		return Direction.valuesStartingWith(this);
	}
	
	public static Direction[] valuesStartingWith(Direction dir) {
		if (!VALUES_FROM_START_CACHE.containsKey(dir)) {
			Direction[] values = new Direction[NUM_DIRECTIONS];
			values[0] = dir;
			for (int i = 1; i < NUM_DIRECTIONS; i++) {
				values[i] = values[i - 1].next();
			}
			VALUES_FROM_START_CACHE.put(dir, values);
		}
		
		return VALUES_FROM_START_CACHE.get(dir);
	}
}
