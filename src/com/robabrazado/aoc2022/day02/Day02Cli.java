package com.robabrazado.aoc2022.day02;

import com.robabrazado.aoc2022.InputGetter;

public class Day02Cli {
	
	// First index: X, Y, or Z (Rock, Paper, or Scissors)
	// Second index: points for self, points against A, B, and C (Rock, Paper, or Scissors)
	private static final int[][] MOVE_POINTS_MATRIX = {
			{1, 3, 0, 6},
			{2, 6, 3, 0},
			{3, 0, 6, 3}
	};
	
	private static final int[] MOVE_OFFSETS = {2, 0, 1}; // X, Y, Z (Lose, Draw, Win)

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(2, 1);
		String[] inputLines = InputGetter.getInputLines(2);
		
		int totalPoints = 0;
		for (String line : inputLines) {
			int[] pointsArr = MOVE_POINTS_MATRIX[line.charAt(2) - 'X'];
			totalPoints += pointsArr[0] + pointsArr[line.charAt(0) - 'A' + 1];
		}
		
		System.out.println("Part 1");
		System.out.println(String.valueOf(totalPoints));
		System.out.println();
		
		
		int totalPoints2 = 0;
		for (String line : inputLines) {
			int theirMoveIdx = line.charAt(0) - 'A';
			int myMoveIdx = (theirMoveIdx + MOVE_OFFSETS[line.charAt(2) - 'X']) % 3;
			int[] pointsArr = MOVE_POINTS_MATRIX[myMoveIdx];
			totalPoints2 += pointsArr[0] + pointsArr[theirMoveIdx + 1];
		}
		
		System.out.println("Part 2");
		System.out.println(String.valueOf(totalPoints2));
		
		
		return;
	}
	
}

