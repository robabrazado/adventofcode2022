package com.robabrazado.aoc2022.day24;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Valley {
	private final Location start;
	private final Location end;
	private final Set<Location> walls = new HashSet<Location>();
	private final Set<Location> blizzardWalls = new HashSet<Location>();
	private final int xCount;
	private final int yCount;
	
	private Map<Location, List<Blizzard>> blizzards;
	
	private Valley(Location start, Location end, Set<Location> walls, Set<Location> blizzardWalls, int xCount, int yCount, Set<Blizzard> blizzards) {
		this.start = start;
		this.end = end;
		this.walls.addAll(walls);
		this.blizzardWalls.addAll(blizzardWalls);
		this.xCount = xCount;
		this.yCount = yCount;
		this.blizzards = createBlizzardMap(blizzards);
		return;
	}
	
	public static Valley parseValley(String[] inputLines) {
		Location start = null;
		Location end = null;
		Set<Location> walls = new HashSet<Location>();
		Set<Location> blizzardWalls = new HashSet<Location>();
		Set<Blizzard> blizzards = new HashSet<Blizzard>();
		int xCount = inputLines[0].length();
		int yCount = inputLines.length;
		
		// Parse map
		for (int y = 0; y < yCount; y++) {
			String line = inputLines[y];
			char[] chars = line.toCharArray();
			for (int x = 0; x < xCount; x++) {
				char c = chars[x];
				if (c == '#') {
					walls.add(Location.getLocation(x, y));
				} else if (c == '.') {
					if (y == 0) {
						start = Location.getLocation(x, y);
					} else if (y == yCount - 1) {
						end = Location.getLocation(x, y);
					}
				} else {
					Location loc = Location.getLocation(x, y);
					Direction facing;
					switch (c) {
					case '^':
						facing = Direction.NORTH;
						break;
					case 'v':
						facing = Direction.SOUTH;
						break;
					case '>':
						facing = Direction.EAST;
						break;
					case '<':
						facing = Direction.WEST;
						break;
					default:
						throw new IllegalArgumentException("Unexpected map character: " + c);
					}
					blizzards.add(new Blizzard(facing, loc));
				}
			}
		}
		
		// Set blizzard walls
		for (int x = 0; x < xCount; x++) {
			blizzardWalls.add(Location.getLocation(x, 0));
			blizzardWalls.add(Location.getLocation(x, yCount - 1));
		}
		
		for (int y = 1; y < yCount - 1; y++) {
			blizzardWalls.add(Location.getLocation(0, y));
			blizzardWalls.add(Location.getLocation(xCount - 1, y));
		}
		
		return new Valley(start, end, walls, blizzardWalls, xCount, yCount, blizzards);
	}
	
	public void advance() {
		Set<Blizzard> newBlizzards = new HashSet<Blizzard>();
		for (List<Blizzard> bList : this.blizzards.values()) {
			for (Blizzard b : bList) {
				Location newLoc = b.move();
				if (this.blizzardWalls.contains(newLoc)) {
					switch (b.getFacing()) {
					case NORTH:
						b = new Blizzard(Direction.NORTH, Location.getLocation(newLoc.x(), yCount - 2));
						break;
					case SOUTH:
						b = new Blizzard(Direction.SOUTH, Location.getLocation(newLoc.x(), 1));
						break;
					case EAST:
						b = new Blizzard(Direction.EAST, Location.getLocation(1, newLoc.y()));
						break;
					case WEST:
						b = new Blizzard(Direction.WEST, Location.getLocation(xCount - 2, newLoc.y()));
						break;
					default:
						throw new RuntimeException("Unrecognized blizzard facing: " + b.getFacing());
					}
				}
				newBlizzards.add(b);
			}
		}
		this.blizzards = createBlizzardMap(newBlizzards);
	}
	
	public int part1() {
		return this.timeToReachGoal(this.start, this.end);
	}
	
	public int part2() {
		int total = this.timeToReachGoal(this.start, this.end);
		total += this.timeToReachGoal(this.end, this.start);
		total += this.timeToReachGoal(this.start, this.end);
		return total;
	}
	
	private int timeToReachGoal(Location first, Location goal) {
		Set<Location> expeditions = new HashSet<Location>();
		boolean reachedGoal = false;
		expeditions.add(first);
		int round = 0;
		
		while (!reachedGoal) {
			round++;
			this.advance();
			Set<Location> newExpeditions = new HashSet<Location>();
			for (Location e : expeditions) {
				for (Location candidate : e.getAllNeighbors()) {
					if (!walls.contains(candidate) && !blizzards.containsKey(candidate) &&
							candidate.x() >= 0 && candidate.x() < xCount &&
							candidate.y() >= 0 && candidate.y() < yCount) {
						if (candidate == goal) {
							reachedGoal = true;
							break;
						}
						newExpeditions.add(candidate);
					}
				}
			}
			expeditions = newExpeditions;
		}
		
		return round;
	}
	
	@Override
	public String toString() {
		return this.toString(new ArrayList<Location>());
	}
	
	public String toString(Collection<Location> expeditionsIn) {
		Set<Location> expeditions = new HashSet<Location>(expeditionsIn);
		StringBuilder strb = new StringBuilder();
		
		for (int y = 0; y < this.yCount; y++) {
			for (int x = 0; x < this.xCount; x++) {
				Location loc = Location.getLocation(x, y);
				List<Blizzard> bList = this.blizzards.get(loc);
				int bCount = bList == null ? 0 : bList.size();
				if (bCount == 1) {
					switch (bList.get(0).getFacing()) {
					case NORTH:
						strb.append('^');
						break;
					case SOUTH:
						strb.append('v');
						break;
					case EAST:
						strb.append('>');
						break;
					case WEST:
						strb.append('<');
						break;
					default:
						strb.append('?');
					}
				} else if (bCount > 9) { // Yikes
					strb.append('*');
				} else if (bCount > 1) {
					strb.append(Integer.toString(bCount).charAt(0));
				} else {
					if (expeditions.contains(loc)) {
						strb.append('E');
					} else if (walls.contains(loc)) {
						strb.append('#');
					} else {
						strb.append('.');
					}
				}
			}
			strb.append('\n');
		}
		
		return strb.toString();
	}
	
	private static Map<Location, List<Blizzard>> createBlizzardMap(Collection<Blizzard> blizzards) {
		Map<Location, List<Blizzard>> retVal = new HashMap<Location, List<Blizzard>>();
		
		for (Blizzard b : blizzards) {
			Location loc = b.getLocation();
			List<Blizzard> bList = retVal.get(loc);
			if (bList == null) {
				bList = new ArrayList<Blizzard>();
				retVal.put(loc, bList);
			}
			bList.add(b);
		}
		
		return retVal;
	}
}
