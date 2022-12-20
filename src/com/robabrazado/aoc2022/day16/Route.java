package com.robabrazado.aoc2022.day16;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {
	private final List<Valve> route;
	private final int timeUsed;
	
	private Integer hashCodeCache = null;
	
	public Route(Valve start) {
		this((List<Valve>) Collections.singletonList(start));
	}
	
	public Route(List<Valve> completeRoute) {
		completeRoute = Collections.unmodifiableList(completeRoute);
		int numSteps = completeRoute.size();
		int mins = 0;
		if (numSteps > 0) {
			Valve prev = completeRoute.get(0);
			for (int i = 1; i < numSteps; i++) {
				Valve curr = completeRoute.get(i);
				mins += curr.getDistance(prev);
				if (curr.getFlowRate() > 0) {
					mins++;
				}
				prev = curr;
			}
		}
		this.route = completeRoute;
		this.timeUsed = mins;
		return;
	}
	
	private Route(List<Valve> route, int time) {
		this.route = route;
		this.timeUsed = time;
	}
	
	public List<Valve> getCompleteRoute() {
		return Collections.unmodifiableList(this.route);
	}
	
	public int getTimeUsed() {
		return this.timeUsed;
	}
	
	public Valve getLastStop() {
		return this.route.get(this.route.size() - 1);
	}
	
	// Returns new object; does not modify current object
	public Route add(Valve nextStep) {
		List<Valve> newRoute = new ArrayList<Valve>(this.route);
		newRoute.add(nextStep);
		int newTime = this.timeUsed;
		newTime += nextStep.getDistance(this.route.get(route.size() - 1));
		if (nextStep.getFlowRate() > 0) {
			newTime++;
		}
		return new Route(newRoute, newTime);
	}
	
	@Override
	public boolean equals(Object o) {
		boolean equal = false;
		
		if (o instanceof Route && this.hashCode() == o.hashCode()) {
			Route other = (Route) o;
			equal = (this.route == null && other.route == null) ||
				(this.route.equals(other.route));
		}
		
		return equal;
	}
	
	@Override
	public int hashCode() {
		if (this.hashCodeCache == null) {
			int code = 0;
			if (this.route != null) {
				code = this.route.hashCode();
			}
			this.hashCodeCache = Integer.valueOf(code);
		}
		return this.hashCodeCache.intValue();
	}
}
