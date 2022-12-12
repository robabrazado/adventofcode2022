package com.robabrazado.aoc2022.day11;

import java.math.BigInteger;
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
	
	private static final BigInteger BIG_INT_THREE = BigInteger.valueOf(3);
	
	private int expectedIndex;
	private Queue<BigInteger> items = new ArrayDeque<BigInteger>();
	private ItemInspector itemInspector;
	private ThrowFinder throwFinder;
	private int inspectionCounter = 0;
	private BigInteger divisor;
	
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
			this.items.add(new BigInteger(s));
		}
		
		// Operation
		parts = lines[2].split("[: =]+");
		this.itemInspector = Monkey.createItemInspector(parts[3], parts[4]);
		
		// Test
		int divisor = parseFinalInt(lines[3], "Test: divisible by ");
		this.divisor = BigInteger.valueOf(divisor);
		int throwIndexTrue = parseFinalInt(lines[4], "If true: throw to monkey ");
		int throwIndexFalse = parseFinalInt(lines[5], "If false: throw to monkey ");
		this.throwFinder = Monkey.createThrowFinder(divisor, throwIndexTrue, throwIndexFalse);
		
	}
	
	public int expectedIndex() {
		return this.expectedIndex;
	}
	
	public void doRound(Monkey[] monkeys, boolean isPart1) {
		while (!this.items.isEmpty()) {
			BigInteger oldWorry = this.items.poll();
			BigInteger newWorry = this.itemInspector.inspect(oldWorry);
			if (isPart1) {
				newWorry = newWorry.divide(BIG_INT_THREE);
			}
			int recipientIndex = this.throwFinder.findTarget(newWorry);
			monkeys[recipientIndex].catchItem(newWorry);
			this.inspectionCounter++;
		}
	}
	
	public int getInspectionCount() {
		return this.inspectionCounter;
	}
	
	private void catchItem(BigInteger newWorry) {
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
				return (BigInteger oldWorry) -> oldWorry.add(new BigInteger(arg));
			case "*":
				if (arg.equals("old")) {
					return (BigInteger oldWorry) -> oldWorry.pow(2);
				} else {
					return (BigInteger oldWorry) -> oldWorry.multiply(new BigInteger(arg));
				}
			default:
				throw new IllegalArgumentException("Unrecognized operator: " + op);
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Unrecognized argument: " + arg);
		}
	}
	
	private static ThrowFinder createThrowFinder(int divisor, int trueMonkeyIndex, int falseMonkeyIndex) {
		return (BigInteger newWorry) -> newWorry.mod(BigInteger.valueOf(divisor)).equals(BigInteger.ZERO) ? trueMonkeyIndex : falseMonkeyIndex;
	}
	
	
	
	@FunctionalInterface
	public interface ItemInspector {
		public BigInteger inspect(BigInteger oldWorry);
	}
	
	@FunctionalInterface
	public interface ThrowFinder {
		public int findTarget(BigInteger newWorry);
	}
}
