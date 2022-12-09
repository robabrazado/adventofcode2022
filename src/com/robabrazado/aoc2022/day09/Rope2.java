package com.robabrazado.aoc2022.day09;

import java.util.HashSet;
import java.util.Set;

public class Rope2 {
	private XY[] knots;
	private Set<XY> tailPositions;
	
	public Rope2(int ropeSize) {
		this.knots = new XY[ropeSize];
		for (int i = 0 ; i < ropeSize; i++) {
			this.knots[i] = new XY();
		}
		this.tailPositions = new HashSet<XY>();
		this.recordTailPosition();
		return;
	}
	
	public void moveHead(XY offset) {
		this.knots[0].move(offset);
		this.moveBody();
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
	
	// Moves non-head knots according to head position; records tail position as visisted
	private void moveBody() {
		for (int i = 1; i < this.knots.length; i++) { // All knots except head
			XY knot = this.knots[i];
			XY offsetFromPrev = knot.getOffsetFrom(this.knots[i - 1]);
			int offsetXAbs = Math.abs(offsetFromPrev.x());
			int offsetYAbs = Math.abs(offsetFromPrev.y());
			int moveOffsetX = 0;
			int moveOffsetY = 0;
			
			if (offsetXAbs > 1 || offsetYAbs > 1) {
				moveOffsetX = getKnotMoveValue(offsetFromPrev.x());
				moveOffsetY = getKnotMoveValue(offsetFromPrev.y());
			}
			XY moveOffset = new XY(moveOffsetX, moveOffsetY);
			knot.move(moveOffset);
		}
		this.recordTailPosition();
		return;
	}
	
	private void recordTailPosition() {
		this.tailPositions.add((XY) this.knots[this.knots.length - 1].clone());
	}
	
	private static int getKnotMoveValue(int offset) {
		int retVal = 0;
		if (offset != 0) {
			retVal = Math.abs(offset) / offset;
		}
		return retVal;
	}
	
}
