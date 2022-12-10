package com.robabrazado.aoc2022.day10;

public class SimpleCpu {
	private int register;
	private int cycleCounter;
	
	
	public SimpleCpu() {
		this.register = 1;
		this.cycleCounter = 1;
		return;
	}
	
	public int getRegisterValue() {
		return this.register;
	}
	
	public int getNextCycleNum() {
		return this.cycleCounter;
	}
	
	// Returns cycle num of cycle about to start (with next instruction)
	// (for Part 1)
	public int execute(String instruction) {
		String[] instParts = instruction.split(" ", 2);
		switch (instParts[0]) {
		case "noop":
			this.cycleCounter++;
			break;
		case "addx":
			this.cycleCounter += 2;
			this.register += Integer.parseInt(instParts[1]);
			break;
		default:
			throw new RuntimeException("Unrecognized instruction: " + instruction);
		}
		
		return this.cycleCounter;
	}
	
	// Returns array of register values, one for each cycle during which this instruction was executed
	// (for Part 2)
	public int[] executeAndPeek(String instruction) {
		int[] retVal;
		String[] instParts = instruction.split(" ", 2);
		switch (instParts[0]) {
		case "noop":
			retVal = new int[] {this.register};
			break;
		case "addx":
			retVal = new int[] {this.register, this.register};
			this.register += Integer.parseInt(instParts[1]);
			break;
		default:
			throw new RuntimeException("Unrecognized instruction: " + instruction);
		}
		
		this.cycleCounter += retVal.length;
		return retVal;
	}
	
	
}
