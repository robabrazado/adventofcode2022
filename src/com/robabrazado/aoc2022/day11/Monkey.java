package com.robabrazado.aoc2022.day11;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Monkey {
	private static final String[] CONSTRUCTION_BLOCK_PREFIXES = new String[] {
			"Monkey ",
			"Starting items: ",
			"Operation: ",
			"Test: ",
			"If true: ",
			"If false: "
	};
	
	private int expectedIndex;
	private Queue<Integer> items = new ArrayDeque<Integer>();
	private ItemInspector itemInspector;
	private ThrowFinder throwFinder;
	private int inspectionCounter = 0;
	
	/*
	 * Monkey 0:
	 *  Starting items: 79, 98
	 *  Operation: new = old * 19
	 *  Test: divisible by 23
	 *    If true: throw to monkey 2
	 *    If false: throw to monkey 3
	 */
	public Monkey(String[] lines) {
		if (lines == null || lines.length != 6) {
			throw new IllegalArgumentException("Malformed Monkey construction block");
		}
		
		// Validate and trim input
		lines = Arrays.copyOf(lines, lines.length);
		for (int i = 0; i < lines.length; i++) {
			String s = lines[i].trim();
			if (!s.startsWith(CONSTRUCTION_BLOCK_PREFIXES[i])) {
				throw new IllegalArgumentException("Expected line at index " + String.valueOf(i) + " to start: " +
					CONSTRUCTION_BLOCK_PREFIXES[i]);
			}
			lines[i] = s;
		}
		
		// Monkey index (according to input)
		this.expectedIndex = parseFinalInt(lines[0].split(":")[0], "Monkey ");
		
		// Starting items
		String[] parts = lines[1].split("[ :,]+");
		for (int i = 2; i < parts.length; i++) {
			String s = parts[i];
			this.items.add(Integer.valueOf(s));
		}
		
		// Operation
		parts = lines[2].split("[: =]+");
		this.itemInspector = Monkey.createItemInspector(parts[3], parts[4]);
		
		// Test
		int divisor = parseFinalInt(lines[3], "Test: divisible by ");
		int throwIndexTrue = parseFinalInt(lines[4], "If true: throw to monkey ");
		int throwIndexFalse = parseFinalInt(lines[5], "If false: throw to monkey ");
		this.throwFinder = Monkey.createThrowFinder(divisor, throwIndexTrue, throwIndexFalse);
		
	}
	
	public int expectedIndex() {
		return this.expectedIndex;
	}
	
	public void doRound(Monkey[] monkeys) {
		while (!this.items.isEmpty()) {
			Integer oldWorry = this.items.poll();
			Integer newWorry = this.itemInspector.inspect(oldWorry);
			newWorry = newWorry.intValue() / 3;
			int recipientIndex = this.throwFinder.findTarget(newWorry);
			monkeys[recipientIndex].catchItem(newWorry);
			this.inspectionCounter++;
		}
	}
	
	public int getInspectionCount() {
		return this.inspectionCounter;
	}
	
	private void catchItem(Integer newWorry) {
		this.items.add(newWorry);
		return;
	}
	
	private static int parseFinalInt(String s, String prefix) {
		if (!s.startsWith(prefix)) {
			throw new IllegalArgumentException("Expected line to start with: " + prefix);
		}
		
		return Integer.parseInt(s.substring(prefix.length()));
	}
	
	private static ItemInspector createItemInspector(String op, String arg) {
		try {
			switch (op) {
			case "+":
				return (Integer oldWorry) -> oldWorry.intValue() + Integer.valueOf(arg);
			case "*":
				if (arg.equals("old")) {
					return (Integer oldWorry) -> oldWorry.intValue() * oldWorry.intValue();
				} else {
					return (Integer oldWorry) -> oldWorry.intValue() * Integer.valueOf(arg);
				}
			default:
				throw new IllegalArgumentException("Unrecognized operator: " + op);
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Unrecognized argument: " + arg);
		}
	}
	
	private static ThrowFinder createThrowFinder(int divisor, int trueMonkeyIndex, int falseMonkeyIndex) {
		return (Integer newWorry) -> (newWorry.intValue() % divisor == 0) ? trueMonkeyIndex : falseMonkeyIndex;
	}
	
	
	
	@FunctionalInterface
	public interface ItemInspector {
		public Integer inspect(Integer oldWorry);
	}
	
	@FunctionalInterface
	public interface ThrowFinder {
		public int findTarget(Integer newWorry);
	}
}
