package com.robabrazado.aoc2022.day10;

import java.util.ArrayDeque;
import java.util.Queue;

import com.robabrazado.aoc2022.InputGetter;

public abstract class Day10Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(10, 1);
		String[] inputLines = InputGetter.getInputLines(10);
		
		
		
		System.out.println("Part 1");
		Queue<Integer> checkpoints = new ArrayDeque<Integer>();
		for (int i = 0; i < 6; i++) {
			checkpoints.add(Integer.valueOf(20 + (40 * i)));
		}
		
		SimpleCpu cpu = new SimpleCpu();
		Integer threshold = checkpoints.poll();
		int signalStrengthSum = 0;
		for (String line : inputLines) {
			int oldValue = cpu.getRegisterValue();
			int nextCycle = cpu.execute(line);
			if (threshold != null && nextCycle > threshold.intValue()) {
				signalStrengthSum += (oldValue * threshold.intValue());
				threshold = checkpoints.poll();
			}
		}
		System.out.println(String.valueOf(signalStrengthSum));
		System.out.println();
		
		System.out.println("Part 2");
		cpu = new SimpleCpu();
		int pixelIndex = 0;
		for (String line : inputLines) {
			int[] values = cpu.executeAndPeek(line);
			for (int value : values) {
				int hPos = pixelIndex % 40;
				System.out.print((hPos >= value - 1 && hPos <= value + 1) ? "#" : ".");
				if (hPos == 39) {
					System.out.println();
				}
				pixelIndex++;
			}
		}
		
		return;
	}

}
