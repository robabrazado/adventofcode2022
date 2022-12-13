package com.robabrazado.aoc2022.day13;

public class PacketPair {
	private PacketDatum left;
	private PacketDatum right;
	
	public PacketPair(PacketDatum left, PacketDatum right) {
		this.left = left;
		this.right = right;
		return;
	}
	
	public boolean isInOrder() {
		int order = left.compareTo(right);
		if (order == 0) {
			throw new IllegalStateException("Packets are equal");
		}
		return order < 0;
	}
	
}
