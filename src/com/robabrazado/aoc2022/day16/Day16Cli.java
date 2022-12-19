package com.robabrazado.aoc2022.day16;

import com.robabrazado.aoc2022.InputGetter;

public abstract class Day16Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(16, 1);
		String[] inputLines = InputGetter.getInputLines(16);
		
		Volcano volcano = new Volcano(inputLines);
		
		System.out.println("Part 1");
		System.out.println(volcano.part1(30));
		System.out.println();
		
		return;
	}
	
}
