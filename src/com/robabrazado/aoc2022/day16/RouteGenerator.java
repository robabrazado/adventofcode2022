package com.robabrazado.aoc2022.day16;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

public class RouteGenerator implements Iterator<Valve[]> {
	private final Valve start;
	private final List<Valve> allDestinations;
	private final int totalMinutes;
	private final List<Queue<Valve>> stepCandidates = new ArrayList<Queue<Valve>>();
	
	public RouteGenerator(Valve start, Set<Valve> destinations, int minutes) {
		this.start = start;
		this.allDestinations = Collections.unmodifiableList(new ArrayList<Valve>(destinations));
		this.totalMinutes = minutes;
		this.stepCandidates.add(new ArrayDeque<Valve>(destinations));
		this.fillCandidates();
		return;
	}
	
	public boolean hasNext() {
		return !this.stepCandidates.isEmpty() && !this.stepCandidates.get(0).isEmpty();
	}
	
	public Valve[] next() {
		this.fillCandidates();
		List<Valve> retVal = new ArrayList<Valve>();
		for (Queue<Valve> candidateQ : this.stepCandidates) {
			Valve curr = candidateQ.peek();
			retVal.add(curr);
		}
		this.increment();
		return retVal.toArray(new Valve[retVal.size()]);
	}
	
	private void fillCandidates() {
		if (!this.hasNext()) {
			throw new NoSuchElementException("No more routes");
		}
		
		int minutesRemaining = this.totalMinutes;
		Valve prev = this.start;
		Set<Valve> alreadyInRoute = new HashSet<Valve>();
		int i = 0;
		while (minutesRemaining > 0) {
			if (i >= this.stepCandidates.size()) {
				Queue<Valve> nextCandidates = new ArrayDeque<Valve>();
				for (Valve v : this.allDestinations) {
					if (!alreadyInRoute.contains(v) && prev.getDistance(v) <= minutesRemaining) {
						nextCandidates.add(v);
					}
				}
				if (!nextCandidates.isEmpty()) {
					this.stepCandidates.add(nextCandidates);
				}
			}
			if (i < this.stepCandidates.size()) {
				Valve curr = this.stepCandidates.get(i).peek();
				alreadyInRoute.add(curr);
				minutesRemaining -= curr.getDistance(prev) + 1;
				i++;
				prev = curr;
			} else {
				// No further candidate steps are in range
				minutesRemaining = 0;
			}
		}
		return;
	}
	
	private void increment() {
		int numCandidateLists = this.stepCandidates.size();
		int i = numCandidateLists - 1;
		this.stepCandidates.get(i).poll();
		while (i > 0 && this.stepCandidates.get(i).isEmpty()) {
			this.stepCandidates.remove(i);
			this.stepCandidates.get(--i).poll();
		}
		return;
	}
}
