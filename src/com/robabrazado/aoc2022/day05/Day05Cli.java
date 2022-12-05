package com.robabrazado.aoc2022.day05;

import java.util.Arrays;

import com.robabrazado.aoc2022.InputGetter;

public class Day05Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(5, 1);
		String[] inputLines = InputGetter.getInputLines(5);
		
		int linesDivider = 0;
		while (!inputLines[linesDivider].isBlank()) {
			linesDivider++;
		}
		
		String[] initLines = Arrays.copyOfRange(inputLines, 0, linesDivider);
		String[] commands = Arrays.copyOfRange(inputLines, linesDivider + 1, inputLines.length);
		
		System.out.println("Part 1");
		CargoStacks stacks = new CargoStacks(initLines);
		stacks.execute9000(commands);
		System.out.println(stacks.getTopCratesMessage());
		System.out.println();
		
		System.out.println("Part 2");
		stacks = new CargoStacks(initLines);
		stacks.execute9001(commands);
		System.out.println(stacks.getTopCratesMessage());
		
		return;
	}

}
