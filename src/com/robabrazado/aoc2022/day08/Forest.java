package com.robabrazado.aoc2022.day08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Forest {
	
	private List<TreeLine> rows;
	private List<TreeLine> cols;
	
	public Forest(String[] inputLines) {
		this.rows = new ArrayList<TreeLine>();
		this.cols = new ArrayList<TreeLine>();
		
		for (String line : inputLines) {
			char[] chars = line.toCharArray();
			int[] heights = new int[chars.length];
			for (int c = 0; c < chars.length; c++) {
				heights[c] = chars[c] - '0';
				if (c > this.cols.size() - 1) {
					this.cols.add(new TreeLine());
				}
				this.cols.get(c).addHeight(heights[c]);
			}
			this.rows.add(new TreeLine(heights));
		}
		
		return;
	}
	
	public int countVisibleTrees() {
		int numRows = this.rows.size();
		int numCols = this.cols.size();
		int visibleCount = 0;
		
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				if (this.rows.get(r).isVisibleInLine(c) || this.cols.get(c).isVisibleInLine(r)) {
					visibleCount++;
				}
			}
		}
		
		return visibleCount;
	}
	
	public int getHighestViewRating() {
		int numRows = this.rows.size();
		int numCols = this.cols.size();
		List<Integer> ratings = new ArrayList<Integer>(numRows * numCols);
		
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				ratings.add(Integer.valueOf(this.rows.get(r).getViewingDistanceProduct(c) * this.cols.get(c).getViewingDistanceProduct(r)));
			}
		}
		
		return Collections.max(ratings).intValue();
	}
	
	
}
