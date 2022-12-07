package com.robabrazado.aoc2022.day07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.robabrazado.aoc2022.InputGetter;

public class Day07Cli {

	public static void main(String[] args) {
//		String[] inputLines = InputGetter.getInputLines(7, 1);
		String[] inputLines = InputGetter.getInputLines(7);
		
		Node root = Node.createTree(inputLines);
		
		List<Node> dirs = new ArrayList<Node>();
		buildDirList(root, dirs);
		
		// Just diagnostic output
//		for (Node dir : dirs) {
//			System.out.println(dir.getName() + " " + dir.getRecursiveSize());
//		}
//		System.out.println();
		
		System.out.println("Part 1");
		int totalUpTo100k = 0;
		for (Node dir : dirs) {
			int size = dir.getRecursiveSize();
			if (size <= 100000) {
				totalUpTo100k += size;
			}
		}
		System.out.println(totalUpTo100k);
		System.out.println();
		
		System.out.println("Part 2");
		Collections.sort(dirs, Comparator.comparing(Node::getRecursiveSize));
		int FILESYSTEM_SIZE = 70000000;
		int SPACE_NEEDED = 30000000;
		int initialSpaceRemaining = FILESYSTEM_SIZE - root.getRecursiveSize();
		int deleteIdx = 0;
		while (initialSpaceRemaining + dirs.get(deleteIdx).getRecursiveSize() < SPACE_NEEDED) {
			deleteIdx++;
		}
		System.out.println(dirs.get(deleteIdx).getRecursiveSize());

	}
	
	private static void buildDirList(Node topDir, List<Node> dirs) {
		Node[] subdirs = topDir.getSubdirectories();
		for (Node subdir : subdirs) {
			buildDirList(subdir, dirs);
		}
		dirs.add(topDir);
		return;
	}
}
