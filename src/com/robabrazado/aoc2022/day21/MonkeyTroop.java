package com.robabrazado.aoc2022.day21;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
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
		return this.solveFor("root");
	}
	
	public BigInteger part2() {
		Map<String, Monkey> part2Map = new HashMap<String, Monkey>(this.monkeyMap);
		
		// Create dependency map
		Map<String, String> dependencies = new HashMap<String, String>();
		for (Iterator<Monkey> it = part2Map.values().iterator(); it.hasNext();) {
			Monkey m = it.next();
			if (m.getType() == Monkey.Type.OPERATION) {
				String name = m.getName();
				for (int i = 0; i <= 1; i++) {
					dependencies.put(m.getValueName(i), name);
				}
			}
		}
		
		// Rearrange necessary nodes to solve for humn
		// This depends on there being only one reference per monkey by another monkey
		String dependsOn = "humn";
		part2Map.remove(dependsOn);
		Monkey lastReplacement = null;
		while (!dependsOn.equals("root")) {
			String replacingName = dependencies.get(dependsOn);
			if (!replacingName.equals("root")) {
				Monkey replacing = part2Map.remove(replacingName);
				lastReplacement = replacing.equivalent(dependsOn);
				part2Map.put(dependsOn, lastReplacement);
			}
			dependsOn = replacingName;
		}
		
		// Use root as implied equals to replace node in the end of the "human dependence" branch
		Monkey root = part2Map.remove("root");
		String swapOut = root.getValueName(0);
		String swapIn = root.getValueName(1);
		if (!lastReplacement.dependsOn(swapOut)) {
			swapOut = root.getValueName(1);
			swapIn = root.getValueName(0);
		}
		lastReplacement.replaceDependent(swapOut, swapIn);
		
		return solveFor("humn", part2Map);
	}
	
	public BigInteger solveFor(String name) {
		return solveFor(name, this.monkeyMap);
	}
	
	public static BigInteger solveFor(String name, Map<String, Monkey> map) {
		Queue<Monkey> monkeyQ = new ArrayDeque<Monkey>(map.values());
		while (!monkeyQ.isEmpty()) {
			Monkey m = monkeyQ.poll();
			if (m.getType() == Monkey.Type.OPERATION && !m.isReady()) {
				for (int i = 0; i <= 1 && !m.isReady(); i++) {
					if (!m.hasValue(i)) {
						Monkey o = map.get(m.getValueName(i));
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
		
		return map.get(name).shout();
	}
}
