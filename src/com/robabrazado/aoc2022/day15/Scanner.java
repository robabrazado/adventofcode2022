package com.robabrazado.aoc2022.day15;

public class Scanner {
	private Coords myPosition;
	private Coords closestBeaconPosition;
	
	public Scanner(Coords pos, Coords beaconPos) {
		this.myPosition = pos;
		this.closestBeaconPosition = beaconPos;
		return;
	}
	
	public int getScanRadius() {
		return this.myPosition.getManhattanDistance(this.closestBeaconPosition);
	}
	
	// Returns null if specified row does not intersect scan range
	public Range getScanSegment(int y) {
		Range retVal = null;
		int rowScanRadius = Math.max(0, this.getScanRadius() - Math.abs(this.myPosition.y() - y));
		if (rowScanRadius > 0) {
			retVal = new Range(this.myPosition.x() - rowScanRadius, this.myPosition.x() + rowScanRadius);
		}
		return retVal;
	}
}
