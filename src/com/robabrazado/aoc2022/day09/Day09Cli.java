package com.robabrazado.aoc2022.day09;

import com.robabrazado.aoc2022.InputGetter;

public class Day09Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(9, 1);
//		String[] inputLines = InputGetter.getInputLines(9, 2);
		String[] inputLines = InputGetter.getInputLines(9);
		
		System.out.println("Part 1");
		Rope rope = new Rope();
		rope.moveHead(inputLines);
		System.out.println(rope.getTailVisitedCount());
		System.out.println();
		
		System.out.println("Part 2");
		Rope2 rope2 = new Rope2(10);
		rope2.moveHead(inputLines);
		System.out.println(rope2.getTailVisitedCount());
		
		return;
	}

}
