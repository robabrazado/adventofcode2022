package com.robabrazado.aoc2022.day24;

public class Blizzard {
	private final Direction facing;
	private Location location;
	
	public Blizzard(Direction facing, Location location) {
		this.facing = facing;
		this.location = location;
		return;
	}
	
	public Direction getFacing() {
		return this.facing;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public Location move() {
		this.location = Location.getLocation(this.location.x() + this.facing.xOffset(), this.location.y() + this.facing.yOffset());
		return this.location;
	}
}
