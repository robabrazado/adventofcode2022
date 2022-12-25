package com.robabrazado.aoc2022.day25;

import com.robabrazado.aoc2022.InputGetter;

public abstract class Day25Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(25, 1);
		String[] inputLines = InputGetter.getInputLines(25);
		
		System.out.println("Part 1");
		BigSnafu total = new BigSnafu(0);
		for (String s : inputLines) {
			total = total.add(new BigSnafu(s));
		}
		System.out.println(total);
		System.out.println();
		
		
	}

}
