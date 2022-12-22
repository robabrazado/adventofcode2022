package com.robabrazado.aoc2022.day22;

import java.util.Arrays;

public enum Facing {
	RIGHT, DOWN, LEFT, UP;
	
	private static final int NUM_FACINGS = Facing.values().length;
	private static final Facing[] ALL_FACINGS = Arrays.copyOf(Facing.values(), NUM_FACINGS);
	
	public int getCodeNumber() {
		return this.ordinal();
	}
	
	public Facing rotate(String s) {
		switch (s) {
		case "R":
			return ALL_FACINGS[(this.ordinal() + 1) % NUM_FACINGS];
		case "L":
			return ALL_FACINGS[((this.ordinal() - 1 % NUM_FACINGS) + NUM_FACINGS) % NUM_FACINGS];
		default:
			throw new IllegalArgumentException("Unrecognized rotate code: " + s);
		}
	}

}
