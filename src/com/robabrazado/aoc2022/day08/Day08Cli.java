package com.robabrazado.aoc2022.day08;

import com.robabrazado.aoc2022.InputGetter;

public class Day08Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(8, 1);
		String[] inputLines = InputGetter.getInputLines(8);
		
		Forest forest = new Forest(inputLines);
		
		System.out.println("Part 1");
		System.out.println(String.valueOf(forest.countVisibleTrees()));
		System.out.println();
		
		System.out.println("Part 2");
		System.out.println(String.valueOf(forest.getHighestViewRating()));
		
		return;
	}

}
