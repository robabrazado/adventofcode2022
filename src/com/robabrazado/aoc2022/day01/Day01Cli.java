package com.robabrazado.aoc2022.day01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.robabrazado.aoc2022.InputGetter;

public class Day01Cli {

	public static void main(String[] args) {
		
//		String[] input = InputGetter.getInputLines(1, 1);
		String[] input = InputGetter.getInputLines(1);
		
		List<Integer> calorieTotals = new ArrayList<Integer>();
		Integer runningTotal = Integer.valueOf(0);
		for (String s : input) {
			if (s.isBlank()) {
				calorieTotals.add(runningTotal);
				runningTotal = Integer.valueOf(0);
			} else {
				runningTotal = Integer.valueOf(runningTotal.intValue() + Integer.parseInt(s));
			}
		}
		calorieTotals.add(runningTotal);
		Collections.sort(calorieTotals);
		int numCalorieTotals = calorieTotals.size();
		System.out.println("Part 1");
		System.out.println(calorieTotals.get(numCalorieTotals - 1));
		System.out.println();
		
		int part2Total = 0;
		for (int i = numCalorieTotals - 3; i < numCalorieTotals; i++) {
			part2Total += calorieTotals.get(i).intValue();
		}
		System.out.println("Part 2");
		System.out.println(part2Total);

	}

}
