package com.robabrazado.aoc2022.day15;

import com.robabrazado.aoc2022.InputGetter;

public abstract class Day15Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(15, 1);
//		int targetRow = 10;
		String[] inputLines = InputGetter.getInputLines(15);
		int targetRow = 2000000;
		
		System.out.println("Part 1");
		ScanningField field = new ScanningField(inputLines);
		System.out.println(field.part1(targetRow));
		System.out.println();
		
		
	}

}
