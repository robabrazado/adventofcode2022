package com.robabrazado.aoc2022.day16_old;

import com.robabrazado.aoc2022.InputGetter;

public abstract class Day16Cli {

	public static void main(String[] args) {
		String[] inputLines = InputGetter.getInputLines(16, 1);
		
		Volcano volcano = new Volcano(inputLines, 30);
		
		System.out.println("Part 1");
		System.out.println(volcano.part1());
		System.out.println();
		
		return;
	}
	
}
