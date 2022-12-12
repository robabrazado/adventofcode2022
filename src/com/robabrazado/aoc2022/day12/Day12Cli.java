package com.robabrazado.aoc2022.day12;

import com.robabrazado.aoc2022.InputGetter;

public abstract class Day12Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(12, 1);
		String[] inputLines = InputGetter.getInputLines(12);
		
		
		System.out.println("Part 1");
		HeightMap heightMap = new HeightMap(inputLines);
		System.out.println(heightMap.part1());
		System.out.println();
		
		System.out.println("Part 2");
		heightMap = new HeightMap(inputLines);
		System.out.println(heightMap.part2());
		
	}

}
