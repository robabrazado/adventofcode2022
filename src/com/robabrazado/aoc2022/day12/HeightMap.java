package com.robabrazado.aoc2022.day12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeightMap {
	
	private List<HeightNode> allNodes;
	private HeightNode startNode;
	private HeightNode endNode;
	
	public HeightMap(String[] inputLines) {
		// Initialize node collection, find start node, and create grid
		List<HeightNode> nodeList = new ArrayList<HeightNode>();
		List<List<HeightNode>> grid = new ArrayList<List<HeightNode>>(inputLines.length);
		for (String s : inputLines) {
			List<HeightNode> row = new ArrayList<HeightNode>(s.length());
			for (char c : s.toCharArray()) {
				HeightNode n = new HeightNode(c);
				row.add(n);
				nodeList.add(n);
				if (n.isStart) {
					this.startNode = n;
				} else if (n.isEnd) {
					this.endNode = n;
				}
			}
			grid.add(row);
		}
		
		if (this.startNode == null) {
			throw new RuntimeException("No start node found");
		}
		
		// Use grid to populate neighbor lists for each node
		int numRows = grid.size();
		for (int row = 0; row < numRows; row++) {
			List<HeightNode> nodeRow = grid.get(row);
			int numCols = nodeRow.size();
			for (int col = 0; col < numCols; col++) {
				HeightNode me = grid.get(row).get(col);
				for (Direction dir : Direction.values()) {
					int nRow = dir.offsetRow(row);
					int nCol = dir.offsetCol(col);
					if (nRow >= 0 && nRow < grid.size()
							&& nCol >= 0 && nCol < grid.get(nRow).size()) {
						me.neighborMap.put(dir, grid.get(nRow).get(nCol));
					}
				}
			}
		}
		
		this.allNodes = Collections.unmodifiableList(nodeList);
		return;
	}
	
	public int part1() {
		int pathLength = Integer.MAX_VALUE;
		List<HeightNode> unvisitedNodes = new ArrayList<HeightNode>(this.allNodes);
		Comparator<HeightNode> nodeSorter = Comparator.comparing(HeightNode::getDistance);
		HeightNode currNode;
		this.startNode.distanceFromStart = 0;
		
		while (!unvisitedNodes.isEmpty() && pathLength == Integer.MAX_VALUE) {
			Collections.sort(unvisitedNodes, nodeSorter);
			currNode = unvisitedNodes.get(0);
			unvisitedNodes.remove(0);
			if (!currNode.isEnd) {
				int newDistance = currNode.getDistance() + 1;
				for (HeightNode neighbor : currNode.neighborMap.values()) {
					if (unvisitedNodes.contains(neighbor) &&
							neighbor.height <= currNode.height + 1 &&
							neighbor.getDistance() > newDistance) {
						neighbor.distanceFromStart = newDistance;
					}
				}
			} else {
				// Current node is endpoint
				pathLength = currNode.getDistance();
			}
		}
		
		if (pathLength == Integer.MAX_VALUE) {
			throw new RuntimeException("No path found to endpoint");
		}
		return pathLength;
	}
	
	public int part2() {
		// Path starts at "end node" and ends at closest elevation "a" node
		int pathLength = Integer.MAX_VALUE;
		List<HeightNode> unvisitedNodes = new ArrayList<HeightNode>(this.allNodes);
		Comparator<HeightNode> nodeSorter = Comparator.comparing(HeightNode::getDistance);
		HeightNode currNode;
		this.endNode.distanceFromStart = 0;
		
		while (!unvisitedNodes.isEmpty() && pathLength == Integer.MAX_VALUE) {
			Collections.sort(unvisitedNodes, nodeSorter);
			currNode = unvisitedNodes.get(0);
			unvisitedNodes.remove(0);
			if (currNode.height > 1) {
				int newDistance = currNode.getDistance() + 1;
				for (HeightNode neighbor : currNode.neighborMap.values()) {
					if (unvisitedNodes.contains(neighbor) &&
							neighbor.height >= currNode.height - 1 &&  // Invert height requirement
							neighbor.getDistance() > newDistance) {
						neighbor.distanceFromStart = newDistance;
					}
				}
			} else {
				// Current node is endpoint
				pathLength = currNode.getDistance();
			}
		}
		
		if (pathLength == Integer.MAX_VALUE) {
			throw new RuntimeException("No path found");
		}
		return pathLength;
	}
	
	
	
	
	
	public enum Direction {
		U	(0, 1),
		D	(0, -1),
		L	(-1, 0),
		R	(1, 0);
		
		private int rowOffset;
		private int colOffset;
		
		Direction(int rowOffset, int colOffset) {
			this.rowOffset = rowOffset;
			this.colOffset = colOffset;
			return;
		}
		
		public int offsetRow(int row) {
			return row + this.rowOffset;
		}
		
		public int offsetCol(int col) {
			return col + this.colOffset;
		}
	}
	
	public class HeightNode {
		private int height;
		private int distanceFromStart = Integer.MAX_VALUE;
		private Map<Direction, HeightNode> neighborMap = new HashMap<Direction, HeightNode>();
		private boolean isStart = false;
		private boolean isEnd = false;
		
		public HeightNode(char c) {
			if (c >= 'a' && c <= 'z') {
				this.height = c - 'a' + 1;
			} else if (c == 'S') {
				this.height = 1;
				this.isStart = true;
			} else if (c == 'E') {
				this.height = 26;
				this.isEnd = true;
			} else {
				throw new IllegalArgumentException("Unrecognized height map value: " + Character.toString(c));
			}
		}
		
		private int getDistance() {
			return this.distanceFromStart;
		}
		
	}
}
