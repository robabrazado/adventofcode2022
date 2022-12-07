package com.robabrazado.aoc2022.day07;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Node {
	private String name;
	private int fileSize; // < 0 means directory
	private Node parent;
	private List<Node> files = new ArrayList<Node>();
	private Map<String, Node> subdirectories = new HashMap<String, Node>();
	
	public Node(String nodeName, int size, Node parentNode) {
		this.name = nodeName;
		this.fileSize = size;
		this.parent = parentNode;
		return;
	}
	
	public Node(String nodeName, int size) {
		this(nodeName, size, (Node) null);
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isDirectory() {
		return this.fileSize < 0;
	}
	
	public Node getParentNode() {
		return this.parent;
	}
	
	public void addChild(Node child) {
		if (child.isDirectory()) {
			this.subdirectories.put(child.name, child);
		} else {
			this.files.add(child);
		}
	}
	
	public Node getSubdirectoryByName(String name) {
		return this.subdirectories.get(name);
	}
	
	private void addChildren(List<String> lsOutput) {
		for (String line : lsOutput) {
			String[] words = line.split(" ");
			int size = -1;
			if (!"dir".equals(words[0])) {
				size = Integer.parseInt(words[0]);
			}
			this.addChild(new Node(words[1], size, this));
		}
	}
	
	// For files, returns size.
	// For dirs, returns size of contained files and all subdirectories' contained files. May count files more than once!
	public int getRecursiveSize() {
		int retVal = this.fileSize;
		
		if (this.isDirectory()) {
			retVal = 0;
			for (Node file : this.files) {
				retVal += file.getRecursiveSize();
			}
			for (Node subdir : this.subdirectories.values()) {
				retVal += subdir.getRecursiveSize();
			}
		}
		
		return retVal;
	}
	
	public Node[] getSubdirectories() {
		return this.subdirectories.values().toArray(new Node[this.subdirectories.size()]);
	}
	
	public static Node createTree(String[] inputLines) {
		Queue<String> terminalIo = new ArrayDeque<String>(Arrays.asList(inputLines));
		
		// Pre-check and initialization
		if (!"$ cd /".equals(terminalIo.peek())) {
			throw new IllegalArgumentException("This doesn't look like the correct terminal I/O to me");
		}
		
		Node root = new Node("/", -1);
		Node currentDir = root;
		terminalIo.poll();
		
		
		String line;
		while ((line = terminalIo.poll()) != null) {
			String[] words = line.split(" ");
			if ("$".equals(words[0])) {
				switch (words[1]) {
					case "ls":
						List<String> lsOutput = new ArrayList<String>();
						while (terminalIo.peek() != null && !terminalIo.peek().startsWith("$")) {
							lsOutput.add(terminalIo.poll());
						}
						currentDir.addChildren(lsOutput);
						break;
					case "cd":
						if ("..".equals(words[2])) {
							currentDir = currentDir.getParentNode();
						} else {
							currentDir = currentDir.getSubdirectoryByName(words[2]);
						}
						break;
					default:
						throw new RuntimeException("Unrecognized command: " + line);
				}
			} else {
				throw new RuntimeException("Unrecognized input: " + line);
			}
		}
		
		return root;
	}
	
}
