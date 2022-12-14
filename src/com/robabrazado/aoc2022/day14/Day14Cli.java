package com.robabrazado.aoc2022.day14;

import com.robabrazado.aoc2022.InputGetter;

public abstract class Day14Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(14, 1);
		String[] inputLines = InputGetter.getInputLines(14);
		
		System.out.println("Part 1");
		Cave cave = new Cave(inputLines);
		System.out.println(cave.part1());
		System.out.println();

	}

}
