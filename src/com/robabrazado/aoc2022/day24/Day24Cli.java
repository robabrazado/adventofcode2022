package com.robabrazado.aoc2022.day24;

import com.robabrazado.aoc2022.InputGetter;

public abstract class Day24Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(24, 1);
//		String[] inputLines = InputGetter.getInputLines(24, 2);
		String[] inputLines = InputGetter.getInputLines(24);
		
		
		System.out.println("Part 1");
		Valley valley = Valley.parseValley(inputLines);
		System.out.println(valley.part1());
		System.out.println();
		

		
	}

}
