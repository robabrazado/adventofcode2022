package com.robabrazado.aoc2022.day09;

import java.util.HashSet;
import java.util.Set;

public class Rope {
	private XY head;
	private XY tail;
	private Set<XY> tailPositions;
	
	public Rope() {
		this.head = new XY();
		this.tail = new XY();
		this.tailPositions = new HashSet<XY>();
		this.tailPositions.add((XY)tail.clone());
		return;
	}
	
	public void moveHead(XY offset) {
//		System.out.println("Moving head " + offset);
		this.head.move(offset);
		this.moveTail();
	}
	
	// This may move the head multiple times
	// Command is {direction} {count}
	public void moveHead(String command) {
		String[] parts = command.split(" ");
		XY offset = Direction.valueOf(parts[0]).offset();
		int count = Integer.parseInt(parts[1]);
		for (int i = 0; i < count; i++) {
			this.moveHead(offset);
		}
		return;
	}
	
	// This will almost certainly move the head multiple times
	// Takes an array of commands a la moveHead(String);
	public void moveHead(String[] commands) {
		for (String command : commands) {
			this.moveHead(command);
		}
		return;
	}
	
	public int getTailVisitedCount() {
		return this.tailPositions.size();
	}
	
	// Moves tail according to head position; records tail position as visisted
	private void moveTail() {
		XY offsetFromHead = this.tail.getOffsetFrom(this.head);
		int offsetXAbs = Math.abs(offsetFromHead.x());
		int offsetYAbs = Math.abs(offsetFromHead.y());
		int moveOffsetX = 0;
		int moveOffsetY = 0;
		
		if (offsetXAbs > 1 || offsetYAbs > 1) {
			moveOffsetX = getTailMoveValue(offsetFromHead.x());
			moveOffsetY = getTailMoveValue(offsetFromHead.y());
		}
		
		XY moveOffset = new XY(moveOffsetX, moveOffsetY);
//		System.out.println("Moving tail " + moveOffset);
		this.tailPositions.add(this.tail.move(moveOffset));
		return;
	}
	
	private static int getTailMoveValue(int offset) {
		int retVal = 0;
		if (offset != 0) {
			retVal = Math.abs(offset) / offset;
		}
		return retVal;
	}
	
}
