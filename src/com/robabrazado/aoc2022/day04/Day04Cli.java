package com.robabrazado.aoc2022.day04;

import java.util.ArrayList;
import java.util.List;

import com.robabrazado.aoc2022.InputGetter;

public class Day04Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(4, 1);
		String[] inputLines = InputGetter.getInputLines(4);
		
		List<Assignment[]> assignmentPairs = new ArrayList<Assignment[]>();
		for (String s : inputLines) {
			assignmentPairs.add(Assignment.parseAssignmentPair(s));
		}
		
		// Part 1
		int containsCount = 0;
		for (Assignment[] assignmentPair : assignmentPairs) {
			if (assignmentPair[0].contains(assignmentPair[1]) || assignmentPair[1].contains(assignmentPair[0])) {
				containsCount++;
			}
		}
		System.out.println("Part 1");
		System.out.println(String.valueOf(containsCount));
		System.out.println();
		
		// Part 2
		int overlapsCount = 0;
		for (Assignment[] assignmentPair : assignmentPairs) {
			if (assignmentPair[0].overlaps(assignmentPair[1])) {
				overlapsCount++;
			}
		}
		System.out.println("Part 2");
		System.out.println(String.valueOf(overlapsCount));
		
		return;
	}
	
}
