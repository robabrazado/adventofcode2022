package com.robabrazado.aoc2022.day23;

import com.robabrazado.aoc2022.InputGetter;

public abstract class Day23Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(23, 1);
		String[] inputLines = InputGetter.getInputLines(23);
		
		
		System.out.println("Part 1");
		Field field = new Field(inputLines);
		System.out.println(field.part1());
		System.out.println();
		
		System.out.println("Part 2");
		field = new Field(inputLines);
		System.out.println(field.part2());

		
	}

}
