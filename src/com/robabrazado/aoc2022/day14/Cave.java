package com.robabrazado.aoc2022.day14;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Cave {
	public static final int SAND_SPAWN_X = 500;
	public static final int SAND_SPAWN_Y = 0;
	
	Map<Integer, BrokenRow> blockedRows; // Row (y) index -> broken row (effectively a sparse array)
	private int bottomRow; // last row index that could possibly block
	private int floorRow; // floor for part 2
	private int sandCount = 0;
	
	public Cave(String[] inputLines) {
		this.blockedRows = new HashMap<Integer, BrokenRow>();
		for (String line : inputLines) {
			this.addRockFormation(line);
		}
		List<Integer> keyList = new ArrayList<Integer>(blockedRows.keySet());
		Collections.sort(keyList);
		this.bottomRow = keyList.get(keyList.size() - 1);
		this.floorRow = this.bottomRow + 2;
		return;
	}
	
	// Spawns and resolves sand until the abyss is reached
	public void caveInUntilAbyss() {
		while (this.spawnSandIntoAbyss() == SandStatus.RESTING) {};
	}
	
	// New sand unit spawned and falls until it rests or reaches the abyss
	// Abyss sand is not counted in sandCount
	public SandStatus spawnSandIntoAbyss() {
		SandStatus status = SandStatus.FALLING;
		int[] sandCoords = new int[] {
			SAND_SPAWN_X,
			SAND_SPAWN_Y
		};
		
		// Move sand until it rests or reaches the abyss
		List<Integer> blockedRowNums = new ArrayList<Integer>(this.blockedRows.keySet());
		Collections.sort(blockedRowNums);
		Queue<Integer> rowNumQ = new ArrayDeque<Integer>(blockedRowNums);
		
		// Fall until stopped (or not)
		while (status == SandStatus.FALLING) {
			// Automatically fall through non-blocked rows
			sandCoords[1] = rowNumQ.peek() - 1;
			
			// Try to move to the next row
			BrokenRow nextRow = this.blockedRows.get(rowNumQ.poll());
			if (!nextRow.contains(sandCoords[0])) { // Straight down
				sandCoords[1]++;
			} else if (sandCoords[0] > 0 && !nextRow.contains(sandCoords[0] - 1)) { // Down and left
				sandCoords[0]--;
				sandCoords[1]++;
			} else if (!nextRow.contains(sandCoords[0] + 1)) { // Down and right
				sandCoords[0]++;
				sandCoords[1]++;
			} else { // Blocked
				// Set to resting and add resting sand to cave
				status = SandStatus.RESTING;
				this.addRestingSand(sandCoords[0], sandCoords[1]);
			}
			
			// Check if we're in the abyss
			if (status == SandStatus.FALLING && sandCoords[1] == this.bottomRow) {
				status = SandStatus.ABYSS;
			}
		}
		
		return status;
	}
	
	// Spawns and resolves sand until the abyss is reached
	public void caveInUntilBlocked() {
		while (this.spawnSandOntoFloor() != SandStatus.BLOCKED) {};
	}
	
	// New sand unit spawned and falls until spawn point is blocked
	// Blocked sand is not counted in sandCount
	public SandStatus spawnSandOntoFloor() {
		SandStatus status = SandStatus.FALLING;
		int[] sandCoords = new int[] {
			SAND_SPAWN_X,
			SAND_SPAWN_Y
		};
		
		// Short out if spawn point is blocked
		if (this.blockedRows.containsKey(SAND_SPAWN_Y) && this.blockedRows.get(SAND_SPAWN_Y).contains(SAND_SPAWN_X)) {
			status = SandStatus.BLOCKED;
		} else {
			// Move sand until it rests
			List<Integer> blockedRowNums = new ArrayList<Integer>(this.blockedRows.keySet());
			Collections.sort(blockedRowNums);
			Queue<Integer> rowNumQ = new ArrayDeque<Integer>(blockedRowNums);
			
			// Fall until stopped
			while (status == SandStatus.FALLING) {
				// Automatically fall through non-blocked rows (until floor)
				if (!rowNumQ.isEmpty()) {
					sandCoords[1] = rowNumQ.peek() - 1;
				} else {
					sandCoords[1] = this.floorRow - 1;
				}
				
				if (sandCoords[1] == this.floorRow - 1) { // Reached the floor
					status = SandStatus.RESTING;
				} else {
					// Try to move to the next row
					BrokenRow nextRow = this.blockedRows.get(rowNumQ.poll());
					if (!nextRow.contains(sandCoords[0])) { // Straight down
						sandCoords[1]++;
					} else if (!nextRow.contains(sandCoords[0] - 1)) { // Down and left
						sandCoords[0]--;
						sandCoords[1]++;
					} else if (!nextRow.contains(sandCoords[0] + 1)) { // Down and right
						sandCoords[0]++;
						sandCoords[1]++;
					} else { // Nowhere to go
						status = SandStatus.RESTING;
					}
				}
			}
			if (status == SandStatus.RESTING) {
				this.addRestingSand(sandCoords[0], sandCoords[1]);
			}
		}
		
		return status;
	}
	
	public int part1() {
		this.caveInUntilAbyss();
		return this.sandCount;
	}
	
	public int part2() {
		this.caveInUntilBlocked();
		return this.sandCount;
	}
	
	private void addRockFormation(String inputLine) {
		String[] parts = inputLine.split(" -> ");
		for (int i = 0; i < parts.length - 1; i++) {
			int[] startCoords = getCoords(parts[i]);
			int[] endCoords = getCoords(parts[i + 1]);
			Range yRange = new Range(startCoords[1], endCoords[1]);
			
			for (int y = yRange.low(); y <= yRange.high(); y++) {
				if (!this.blockedRows.containsKey(y)) {
					this.blockedRows.put(y, new BrokenRow());
				}
				this.blockedRows.get(y).addRange(startCoords[0], endCoords[0]);
			}
		}
		return;
	}
	
	private void addRestingSand(int x, int y) {
		if (!this.blockedRows.containsKey(y)) {
			this.blockedRows.put(y,  new BrokenRow());
		}
		this.blockedRows.get(y).addPoint(x);
		this.sandCount++;
		return;
	}
	
	private static int[] getCoords(String s) {
		String[] parts = s.split(",");
		return new int[] {
			Integer.parseInt(parts[0]),
			Integer.parseInt(parts[1])
		};
	}
	
	
	
	private enum SandStatus {
		FALLING, RESTING, ABYSS, BLOCKED;
	}
}
