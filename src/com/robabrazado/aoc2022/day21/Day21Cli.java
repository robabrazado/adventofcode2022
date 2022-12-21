package com.robabrazado.aoc2022.day21;

import com.robabrazado.aoc2022.InputGetter;

public abstract class Day21Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(21, 1);
		String[] inputLines = InputGetter.getInputLines(21);
		
		MonkeyTroop troop = new MonkeyTroop(inputLines);
		
		System.out.println("Part 1");
		System.out.println(troop.part1());
		System.out.println();
		
		
	}

}
