package com.robabrazado.aoc2022.day22;

public class Meeple {
	private int row;
	private int col;
	private Facing facing;
	
	public Meeple(int row, int col, Facing facing) {
		this.row = row;
		this.col = col;
		this.facing = facing;
		return;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return this.col;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public Facing getFacing() {
		return this.facing;
	}

	public void rotate(String dir) {
		this.facing = this.facing.rotate(dir);
	}
	
	public int getFacingCodeNumber() {
		return this.facing.getCodeNumber();
	}
	
	@Override
	public String toString() {
		return "(" + this.row + "," + this.col + ") " + this.facing;
	}
}
