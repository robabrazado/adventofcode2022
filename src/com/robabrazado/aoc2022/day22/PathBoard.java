package com.robabrazado.aoc2022.day22;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathBoard {
	public static final char WALL_CHAR = '#';
	public static final char TILE_CHAR = '.';
	public static final char SPACE_CHAR = ' ';
	
	private static final Pattern PATH_TOKENIZER = Pattern.compile("L|R|[0-9]+");
	
	private final Line[] rows; // Index -1 offset from "row number"
	private final Line[] cols; // Index -1 offset from "col number"
	private final String path;
	
	public PathBoard(String[] inputLines) {
		Queue<String> inputQ = new ArrayDeque<String>(Arrays.asList(inputLines));
		List<Line> rowList = new ArrayList<Line>();
		Map<Integer, LineBuilder> colMap = new HashMap<Integer, LineBuilder>();
		
		// Parse rows and cols
		// NOTE: This model breaks HARD if there are non-contiguous rows or columns
		int row = 1;
		while (!inputQ.peek().isBlank()) {
			String rowLine = inputQ.poll();
			int numCols = rowLine.length();
			rowList.add(new Line(rowLine));
			
			for (int col = 1; col <= numCols; col++) {
				char c = rowLine.charAt(col - 1);
				if (c == TILE_CHAR || c == WALL_CHAR) {
					if (!colMap.containsKey(col)) {
						colMap.put(col, new LineBuilder(row));
					}
					if (c == WALL_CHAR) {
						colMap.get(col).addWall(row);
					}
				}
			}
			for (Integer col : colMap.keySet()) {
				if (col > numCols || rowLine.charAt(col - 1) == SPACE_CHAR) {
					if (colMap.containsKey(col)) {
						LineBuilder column = colMap.get(col);
						if (!column.hasHigh()) {
							colMap.get(col).setHigh(row - 1);
						}
					}
				}
			}
			row++;
		}
		this.rows = rowList.toArray(new Line[rowList.size()]);
		int numCols = colMap.size();
		this.cols = new Line[numCols];
		for (int i = 0; i < numCols; i++) {
			int col = i + 1;
			LineBuilder builder = colMap.get(col);
			if (builder == null) {
				throw new RuntimeException("Unexpectedly missing builder for column " + Integer.toString(col));
			}
			if (!builder.hasHigh()) {
				builder.setHigh(row - 1);
			}
			this.cols[i] = colMap.get(col).toLine();
		}
		
		// Parse path
		inputQ.poll(); // Toss empty input line
		this.path = inputQ.poll();
		
		return;
	}
	
	public int part1() {
		Meeple meeple = new Meeple(1, this.rows[0].getFirstOpenTile(), Facing.RIGHT);
		Matcher tokenizer = PATH_TOKENIZER.matcher(this.path);
		while (tokenizer.find()) {
			String s = tokenizer.group();
			switch (s) {
			case "L":
			case "R":
				meeple.rotate(s);
				break;
			default:
				this.move(meeple, Integer.valueOf(s));
			}
		}
		
		
		return (meeple.getRow() * 1000) + (meeple.getCol() * 4) + meeple.getFacingCodeNumber();
	}
	
	private void move(Meeple meeple, int len) {
		switch (meeple.getFacing()) {
		case RIGHT:
			meeple.setCol(this.rows[meeple.getRow() - 1].getNewPositionAsc(meeple.getCol(), len));
			break;
		case DOWN:
			meeple.setRow(this.cols[meeple.getCol() - 1].getNewPositionAsc(meeple.getRow(), len));
			break;
		case LEFT:
			meeple.setCol(this.rows[meeple.getRow() - 1].getNewPositionDesc(meeple.getCol(), len));
			break;
		case UP:
			meeple.setRow(this.cols[meeple.getCol() - 1].getNewPositionDesc(meeple.getRow(), len));
			break;
		default:
			throw new RuntimeException("Unrecognized facing: " + meeple.getFacing());
		}
	}
	
}
