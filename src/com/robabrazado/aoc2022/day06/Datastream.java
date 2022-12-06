package com.robabrazado.aoc2022.day06;

import java.util.HashSet;
import java.util.Set;

public class Datastream {
	private String data;
	
	public Datastream(String datastream) {
		this.data = datastream;
		return;
	}
	
	public int packetStartsAfterLen() {
		return startsAfterLen(4);
	}
	
	public int messageStartsAfterLen() {
		return startsAfterLen(14);
	}
	
	private int startsAfterLen(int markerLen) {
		boolean isStart = false;
		int dataLen = this.data.length();
		int startAt = markerLen;
		while (!(isStart = doesMarkerStartAfter(startAt, markerLen)) && startAt <= dataLen) {
			startAt++;
		}
		if (!isStart) {
			// This shouldn't happen
			throw new RuntimeException("No start-of-message packet found");
		}
		return startAt;
	}
	
	private boolean doesMarkerStartAfter(int startChar, int markerLen) {
		Set<Character> charSet = new HashSet<Character>();
		
		for (int offset = 1; offset <= markerLen; offset++) {
			charSet.add(Character.valueOf(this.data.charAt(startChar - offset)));
		}
		
		return charSet.size() == markerLen;
	}
}
