package com.robabrazado.aoc2022.day15;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ScanningField {
	
	private Map<Coords, Scanner> scanners;
	private Set<Coords> beacons;
	
	public ScanningField(String[] inputLines) {
		this.scanners = new HashMap<Coords, Scanner>();
		this.beacons = new HashSet<Coords>();
		
		for (String line : inputLines) {
			String[] parts = line.split("Sensor at |: closest beacon is at ", 3);
			Coords scannerCoords = new Coords(parts[1]);
			Coords beaconCoords = new Coords(parts[2]);
			
			this.scanners.put(scannerCoords, new Scanner(scannerCoords, beaconCoords));
			this.beacons.add(beaconCoords);
		}
		
		return;
	}
	
	public BrokenRow getScannedRow(int targetRow) {
		// Count all scannable spaces in row
		BrokenRow row = new BrokenRow();
		for (Scanner scanner : scanners.values()) {
			Range r = scanner.getScanSegment(targetRow);
			if (r != null) {
				row.addRange(r);
			}
		}
		return row;
	}
	
	public int scannedSpacesInRow(int targetRow) {
		// Count all scannable spaces in row
		BrokenRow row = this.getScannedRow(targetRow);
		int scannedCount = row.count();
		
		// Account for spaces with scanners
		scannedCount -= countItemsInRow(this.scanners.keySet(), targetRow);
		
		// Account for spaces with beacons
		scannedCount -= countItemsInRow(this.beacons, targetRow);
		
		return scannedCount;
	}
	
	public int part1(int targetRow) {
		return this.scannedSpacesInRow(targetRow);
	}
	
	public BigInteger part2(int maxRow) {
		Coords solutionCoords = null;
		
		for (int y = 0; y <= maxRow && solutionCoords == null; y++) {
			BrokenRow row = this.getScannedRow(y);
			Range[] ranges = row.getRanges();
			// This is WAY not generally applicable! Assumes a LOT about input conditions
			if (ranges.length > 1) {
				solutionCoords = new Coords(ranges[0].high() + 1, y);
			}
		}
		
		return BigInteger.valueOf(solutionCoords.x()).multiply(BigInteger.valueOf(4000000)).add(BigInteger.valueOf(solutionCoords.y()));
	}
	
	private static int countItemsInRow(Collection<Coords> coords, int row) {
		int count = 0;
		
		for (Coords c : coords) {
			if (c.y() == row) {
				count++;
			}
		}
		
		return count;
	}
}
