package com.robabrazado.aoc2022.day22;

import com.robabrazado.aoc2022.InputGetter;

public abstract class Day22Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(22, 1);
		String[] inputLines = InputGetter.getInputLines(22);
		
		PathBoard board = new PathBoard(inputLines);
		System.out.println("Part 1");
		System.out.println(board.part1());
		System.out.println();
		
		System.out.println("Part 2");

		
	}

}
