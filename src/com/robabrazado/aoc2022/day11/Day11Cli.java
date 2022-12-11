package com.robabrazado.aoc2022.day11;

import com.robabrazado.aoc2022.InputGetter;

public abstract class Day11Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(11, 1);
		String[] inputLines = InputGetter.getInputLines(11);
		
		MonkeyPack pack = new MonkeyPack(inputLines);
		
		System.out.println("Part 1");
		System.out.println(pack.part1());
		System.out.println();
		
		
		return;
	}

}
