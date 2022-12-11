package com.robabrazado.aoc2022.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MonkeyPack {
	private Monkey[] monkeys;
	
	public MonkeyPack(String[] inputLines) {
		List<Monkey> monkeyList = new ArrayList<Monkey>();
		for (int i = 0; i < inputLines.length; i += 7) {
			monkeyList.add(new Monkey(Arrays.copyOfRange(inputLines, i, i + 6)));
		}
		this.monkeys = monkeyList.toArray(new Monkey[monkeyList.size()]);
		
		// Just an index validation
		for (int i = 0; i < this.monkeys.length; i++) {
			if (this.monkeys[i].expectedIndex() != i) {
				throw new RuntimeException("Found monkey " + String.valueOf(this.monkeys[i].expectedIndex() + " at index " + String.valueOf(i)));
			}
		}
		
		return;
	}
	
	public void doTurn(boolean divideWorry) {
		for (Monkey m : this.monkeys) {
			m.doRound(this.monkeys);
		}
		return;
	}
	
	public int part1() {
		for (int i = 0; i < 20; i++) {
			this.doTurn(true);
		}
		
		Monkey[] arrCopy = Arrays.copyOf(this.monkeys, this.monkeys.length);
		Arrays.sort(arrCopy, Comparator.comparing(Monkey::getInspectionCount).reversed());
		return arrCopy[0].getInspectionCount() * arrCopy[1].getInspectionCount();
	}
	
	public int part2() {
		throw new RuntimeException("Not yet implemented");
	}
}
