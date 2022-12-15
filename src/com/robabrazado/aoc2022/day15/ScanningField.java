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
	
	public BigInteger part2(int maxDimension) {
		Range scanRange = new Range(0, maxDimension);
		Coords solutionCoords = null;
		
		for (int y = 0; y <= maxDimension && solutionCoords == null; y++) {
			/* This part was changed after I submitted my puzzle answers.
			 * The original version worked for my specific puzzle input,
			 * but I realized later it would NOT work for ALL inputs
			 * (specifically if the correct spot was in the first or last
			 * columns. This refit should work for all input, but I haven't
			 * verified that.
			 * 
			 * It still assumes a single unscanned cell in the entire
			 * search grid and, by extension, a single unscanned cell
			 * in a given row.
			 */
			BrokenRow row = this.getScannedRow(y);
			if (!row.contains(scanRange)) {
				// Find the first unscanned space within the scan range
				Range[] ranges = row.getRanges();
				int x = -1;
				for (int i = 0; i < ranges.length && !scanRange.contains(x); i++) {
					Range r = ranges[i];
					x = scanRange.contains(r.low() - 1) ? r.low() - 1 : r.high() + 1;
				}
				
				solutionCoords = new Coords(x, y);
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
