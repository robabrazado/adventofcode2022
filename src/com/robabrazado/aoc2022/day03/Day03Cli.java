package com.robabrazado.aoc2022.day03;

import java.util.Arrays;

import com.robabrazado.aoc2022.InputGetter;

public class Day03Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(3, 1);
		String[] inputLines = InputGetter.getInputLines(3);
		
		// Part 1
		int priorityTotal1 = 0;
		for (String line : inputLines) {
			priorityTotal1 += sharedItemPriority(line);
		}
		System.out.println("Part 1");
		System.out.println(String.valueOf(priorityTotal1));
		System.out.println();
		
		// Part 2
		if (inputLines.length % 3 != 0) {
			throw new RuntimeException("Rucksack count not divisible by 3");
		}
		
		int priorityTotal2 = 0;
		for (int i = 0; i < inputLines.length; i += 3) {
			priorityTotal2 += getItemPriority(getCommonItem(Arrays.copyOfRange(inputLines, i, i + 3)));
		}
		System.out.println("Part 2");
		System.out.println(priorityTotal2);
		
		return;
	}
	
	public static int sharedItemPriority(String line) {
		int sackSize = line.length();
		if (sackSize % 2 != 0) {
			throw new IllegalArgumentException("Initial item count is odd");
		}
		int compartmentSize = sackSize / 2;
		
		String items1 = line.substring(0, compartmentSize);
		String items2 = line.substring(compartmentSize);
		
		char sharedItem = 0;
		for (int i = 0; i < compartmentSize && sharedItem == 0; i++) {
			char seekItem = items1.charAt(i);
			if (items2.indexOf(seekItem) >= 0) {
				sharedItem = seekItem;
			}
		}
		
		if (sharedItem == 0) {
			throw new RuntimeException("No shared item found: " + line);
		}
		
		return getItemPriority(sharedItem);
	}
	
	public static char getCommonItem(String[] group) {
		char sharedItem = 0;
		for (int i = 0; i < group[0].length() && sharedItem == 0; i++) {
			char c = group[0].charAt(i);
			if (group[1].indexOf(c) >= 0 && group[2].indexOf(c) >= 0) {
				sharedItem = c;
			}
		}
		if (sharedItem == 0) {
			throw new RuntimeException("No shared item found in group: " + group[0] + "/" + group[1] + "/" + group[2]);
		}
		return sharedItem;
	}
	
	// Return value not reliable if c is outside [A-Za-z]
	public static int getItemPriority(char c) {
		if (c > 'Z') {
			return c - 'a' + 1;
		} else {
			return c - 'A' + 27;
		}
	}
}
