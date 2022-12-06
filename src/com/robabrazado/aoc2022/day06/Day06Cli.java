package com.robabrazado.aoc2022.day06;

import com.robabrazado.aoc2022.InputGetter;

public class Day06Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(6, 1);
		String[] inputLines = InputGetter.getInputLines(6);
		
		Datastream ds = new Datastream(inputLines[0]);
		
		System.out.println("Part 1");
		System.out.println(String.valueOf(ds.packetStartsAfterLen()));
		System.out.println();
		
		System.out.println("Part 2");
		System.out.println(String.valueOf(ds.messageStartsAfterLen()));
		
		return;
	}

}
