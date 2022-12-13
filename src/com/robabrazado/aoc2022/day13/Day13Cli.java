package com.robabrazado.aoc2022.day13;

import com.robabrazado.aoc2022.InputGetter;

public abstract class Day13Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(13, 1);
		String[] inputLines = InputGetter.getInputLines(13);
		
		
		System.out.println("Part 1");
		System.out.println(PacketDatum.part1(inputLines));
		System.out.println();
		
		System.out.println("Part 2");
		System.out.println(PacketDatum.part2(inputLines));
		
		return;
	}

}
