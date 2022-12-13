package com.robabrazado.aoc2022.day13;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.ModuleElement.DirectiveVisitor;

public class PacketDatum implements Comparable<PacketDatum> {
	private static final Pattern INPUT_STRING_TOKENIZER = Pattern.compile("\\[|\\]|,|[0-9]+");
	
	private Type type;
	private PacketDatum[] listData = null;
	private Integer intData = null;
	
	public PacketDatum(String s) {
		if (s.startsWith("[")) {
			this.type = Type.LIST;
			// Assuming well-formed input, dropping the enclosing brackets
			s = s.substring(1, s.length() - 1);
			List<PacketDatum> pdList = new ArrayList<PacketDatum>();
			if (!s.isEmpty()) {
				Matcher tokenizer = INPUT_STRING_TOKENIZER.matcher(s);
				int bracketLevel = 0;
				StringBuilder substring = new StringBuilder();
				
				while (tokenizer.find()) {
					String part = tokenizer.group();
					
					switch (part) {
					case "[":
						bracketLevel++;
						break;
					case "]":
						bracketLevel--;
						break;
					}
					
					if (bracketLevel == 0 && part.equals(",")) {
						pdList.add(new PacketDatum(substring.toString()));
						substring = new StringBuilder();
					} else {
						substring.append(part);
					}
				}
				pdList.add(new PacketDatum(substring.toString()));
			}
			
			this.listData = pdList.toArray(new PacketDatum[pdList.size()]);
		} else {
			this.type = Type.INT;
			try {
				this.intData = Integer.valueOf(s);
			} catch (NumberFormatException nfe) {
				throw new RuntimeException("Expected a number; got: " + s);
			}
		}
		
		return;
	}
	
	public PacketDatum toListDatum() {
		if (this.type == Type.INT) {
			return new PacketDatum("[" + this.intData.toString() + "]");
		} else {
			return this;
		}
	}
	
	public int compareTo(PacketDatum other) {
		int retVal = 0;
		
		if (this.type == Type.INT) {
			if (other.type == Type.INT) {
				return this.intData.compareTo(other.intData);
			} else {
				return this.toListDatum().compareTo(other);
			}
		} else {
			if (other.type == Type.INT) {
				return this.compareTo(other.toListDatum());
			} else {
				int i = 0;
				while (retVal == 0 && i < this.listData.length && i < other.listData.length) {
					retVal = this.listData[i].compareTo(other.listData[i]);
					i++;
				}
				if (retVal == 0) {
					retVal = this.listData.length - other.listData.length;
				}
			}
		}
		
		return retVal;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equal = false;
		
		if (o instanceof PacketDatum) {
			equal = ((PacketDatum) o).compareTo(this) == 0;
		}
		
		return equal;
	}
	
	@Override
	public String toString() {
		switch (this.type) {
		case INT:
			return this.intData.toString();
		case LIST:
			StringBuilder strb = new StringBuilder("[");
			for (PacketDatum pd : this.listData) {
				strb.append(pd.toString());
				strb.append(',');
			}
			if (this.listData.length > 0) {
				strb.deleteCharAt(strb.length() - 1);
			}
			strb.append("]");
			return strb.toString();
		default:
			throw new IllegalStateException("Unsupported PacketDatum type");
		}
	}
	
	public static PacketPair[] parsePacketPairs(String[] inputLines) {
		List<PacketPair> pairs = new ArrayList<PacketPair>();
		
		Queue<String> lines = new ArrayDeque<String>(Arrays.asList(inputLines));
		while (!lines.isEmpty()) {
			while (lines.peek().isBlank()) {
				lines.poll();
			}
			pairs.add(new PacketPair(new PacketDatum(lines.poll()), new PacketDatum(lines.poll())));
		}
		
		return pairs.toArray(new PacketPair[pairs.size()]);
	}
	
	public static int part1(String[] inputLines) {
		PacketPair[] packetPairs = PacketDatum.parsePacketPairs(inputLines);
		int indexSum = 0;
		for (int i = 0; i < packetPairs.length; i++) {
			if (packetPairs[i].isInOrder()) {
				indexSum += (i + 1);
			}
		}
		return indexSum;
	}
	
	public static int part2(String[] inputLines) {
		PacketDatum[] dividerPackets = new PacketDatum[] {
			new PacketDatum("[[2]]"),
			new PacketDatum("[[6]]")
		};
		List<PacketDatum> packets = new ArrayList<PacketDatum>();
		
		for (String line : inputLines) {
			if (!line.isBlank()) {
				packets.add(new PacketDatum(line));
			}
		}
		packets.addAll(Arrays.asList(dividerPackets));
		
		Collections.sort(packets);
		
		return (packets.indexOf(dividerPackets[0]) + 1) * (packets.indexOf(dividerPackets[1]) + 1);
	}
	
	
	public enum Type {
		LIST, INT;
	}
}
