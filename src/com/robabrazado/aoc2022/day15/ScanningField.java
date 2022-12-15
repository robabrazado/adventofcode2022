package com.robabrazado.aoc2022.day15;

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
	
	public int part1(int targetRow) {
		// Count all scannable spaces in row
		BrokenRow row = new BrokenRow();
		for (Scanner scanner : scanners.values()) {
			Range r = scanner.getScanSegment(targetRow);
			if (r != null) {
				row.addRange(r);
			}
		}
		int scannedCount = row.count();
		
		// Account for spaces with scanners
		scannedCount -= countItemsInRow(this.scanners.keySet(), targetRow);
		
		// Account for spaces with beacons
		scannedCount -= countItemsInRow(this.beacons, targetRow);
		
		return scannedCount;
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
