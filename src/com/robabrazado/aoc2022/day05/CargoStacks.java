package com.robabrazado.aoc2022.day05;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CargoStacks {
	private List<Deque<Character>> stacks;
	
	/*
	 *     [D]    
	 * [N] [C]    
	 * [Z] [M] [P]
 	 *  1   2   3
	 */
	public CargoStacks(String[] lines) {
		// Last line is the stack indices
		String stackLabels = lines[lines.length - 1].trim();
		
		// Initialize collection of stacks
		int numStacks = Integer.parseInt(stackLabels.substring(stackLabels.lastIndexOf(' ') + 1));
		this.stacks = new ArrayList<Deque<Character>>(numStacks);
		
		// Initialize each stack
		for (int i = 0; i < numStacks; i++) {
			this.stacks.add(new ArrayDeque<Character>(lines.length - 1));
		}
		
		// Populate crate stacks
		for (int lineIdx = lines.length - 2; lineIdx >= 0; lineIdx--) {
			String line = lines[lineIdx];
			for (int stackIdx = 0; stackIdx < numStacks; stackIdx++) {
				int labelIdx = (stackIdx * 4) + 1;
				if (line.length() > labelIdx) {
					char c = line.charAt(labelIdx);
					if (c != ' ') {
						this.stacks.get(stackIdx).push(Character.valueOf(c));
					}
				}
			}
		}
	}
	
	/*
	 * move 1 from 2 to 1
	 * move 3 from 1 to 3
	 * move 2 from 2 to 1
	 * move 1 from 1 to 2
	 */
	public void execute9000(String[] commands) {
		for (String s : commands) {
			String[] params = s.split("[ \\[\\]]");
			int moveCount = Integer.parseInt(params[1]);
			int fromIdx = Integer.parseInt(params[3]) - 1;
			int toIdx = Integer.parseInt(params[5]) - 1;
			
			for (int i = 0; i < moveCount; i++) {
				this.stacks.get(toIdx).push(this.stacks.get(fromIdx).pop());
			}
		}
	}
	
	public void execute9001(String[] commands) {
		for (String s : commands) {
			String[] params = s.split("[ \\[\\]]");
			int moveCount = Integer.parseInt(params[1]);
			int fromIdx = Integer.parseInt(params[3]) - 1;
			int toIdx = Integer.parseInt(params[5]) - 1;
			
			Deque<Character> craneLoad = new ArrayDeque<Character>(moveCount);
			for (int i = 0; i < moveCount; i++) {
				craneLoad.push(this.stacks.get(fromIdx).pop());
			}
			for(int i = 0; i < moveCount; i++) {
				this.stacks.get(toIdx).push(craneLoad.pop());
			}
		}
	}
	
	public String getTopCratesMessage() {
		StringBuilder sb = new StringBuilder();
		for (Deque<Character> stack : this.stacks) {
			sb.append(stack.peek());
		}
		return sb.toString();
	}
}
