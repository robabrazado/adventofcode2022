package com.robabrazado.aoc2022.day23;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Field {
	private static final char ELF_CHAR = '#';
	
	private final Set<Position> elves = new HashSet<Position>();
	private Direction startDirection;
	
	public Field(String[] inputLines) {
		int y = 0;
		for (String line : inputLines) {
			int len = line.length();
			for (int x = 0; x < len; x++) {
				if (line.charAt(x) == ELF_CHAR) {
					this.elves.add(new Position(x, y));
				}
			}
			y++;
		}
		startDirection = Direction.NORTH;
		return;
	}
	
	public int part1() {
		for (int i = 1; i <= 10; i++) {
			this.diffuseRound();
		}
		return this.getSmallestRectangleArea() - this.elves.size();
	}
	
	// Returns number of elves that successfully moved
	public int diffuseRound() {
		Map<Position, List<Position>> proposedPositions = new HashMap<Position, List<Position>>();
		int count = 0;
		Direction[] dirs = this.startDirection.valuesStartingWithCurrent();
		int numDirections = dirs.length;
		
		// Collect every proposed new location along with all elves requesting that location
		for (Position self : this.elves) {
			if (!Collections.disjoint(this.elves, self.getNeighbors())) {
				Position proposedLocation = null;
				for (int i = 0; i < numDirections && proposedLocation == null; i++) {
					List<Position> neighbors = self.getNeighbors(dirs[i]);
					if (Collections.disjoint(this.elves, neighbors)) {
						proposedLocation = neighbors.get(1);
						if (!proposedPositions.containsKey(proposedLocation)) {
							proposedPositions.put(proposedLocation, new ArrayList<Position>());
						}
						proposedPositions.get(proposedLocation).add(self);
					}
				}
			}
		}
		
		// Scan proposed locations and either execute the move or reject contested locations
		for (Position newPosition : proposedPositions.keySet()) {
			List<Position> candidates = proposedPositions.get(newPosition);
			int numCandidates = candidates.size();
			if (numCandidates == 0) {
				throw new RuntimeException("Found proposed new location with no old location?! This shouldn't happen?!");
			}
			
			if (numCandidates == 1) {
				this.elves.add(newPosition);
				this.elves.remove(candidates.get(0));
				count++;
			} // else do nothing
		}
		
		// Increment start direction
		this.startDirection = this.startDirection.next();
		
		return count;
	}
	
	public int getSmallestRectangleArea() {
		int area = 0;
		
		if (this.elves.size() > 0) {
			List<Position> posList = new ArrayList<Position>(this.elves);
			Collections.sort(posList, Comparator.comparing(Position::x));
			int xLo = posList.get(0).x();
			Collections.sort(posList, Comparator.comparing(Position::x).reversed());
			int xHi = posList.get(0).x();
			Collections.sort(posList, Comparator.comparing(Position::y));
			int yLo = posList.get(0).y();
			Collections.sort(posList, Comparator.comparing(Position::y).reversed());
			int yHi = posList.get(0).y();
			area = (xHi - xLo + 1) * (yHi - yLo + 1);
		}
		
		return area;
	}
	
}
