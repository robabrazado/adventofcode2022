package com.robabrazado.aoc2022.day21;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class MonkeyTroop {
	
	private final Map<String, Monkey> monkeyMap = new HashMap<String, Monkey>();
	
	public MonkeyTroop(String[] inputLines) {
		for (String line : inputLines) {
			String[] parts = line.split(": ");
			String name = parts[0];
			String job = parts[1];
			if (job.contains(" ")) {
				this.monkeyMap.put(name, new Monkey(name, job));
			} else {
				this.monkeyMap.put(name, new Monkey(name, Integer.valueOf(job)));
			}
		}
	}
	
	public BigInteger part1() {
		Queue<Monkey> monkeyQ = new ArrayDeque<Monkey>(this.monkeyMap.values());
		while (!monkeyQ.isEmpty()) {
			Monkey m = monkeyQ.poll();
			if (m.getType() == Monkey.Type.OPERATION && !m.isReady()) {
				for (int i = 0; i <= 1 && !m.isReady(); i++) {
					if (!m.hasValue(i)) {
						Monkey o = this.monkeyMap.get(m.getValueName(i));
						if (o.isReady()) {
							m.setValue(i, o.shout());
						}
					}
				}
				if (!m.isReady()) {
					monkeyQ.add(m);
				}
			}
		}
		
		return this.monkeyMap.get("root").shout();
	}
}
