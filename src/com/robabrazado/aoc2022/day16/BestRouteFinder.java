package com.robabrazado.aoc2022.day16;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BestRouteFinder {
	private final List<Valve> allDestinations;
	private final Valve start;
	private final int timeLimit;
	
	private final Map<Route, Integer> pressureCache = new HashMap<Route, Integer>();
	
	public BestRouteFinder(Collection<Valve> allValves, int timeLimit, String startName) {
		List<Valve> tmpDests = new ArrayList<Valve>();
		Valve tmpStart = null;
		for (Valve v : allValves) {
			if (v.getName().equals(startName)) {
				if (tmpStart != null) {
					throw new IllegalArgumentException("More than one start valve found");
				}
				tmpStart = v;
			}
			if (v.getFlowRate() > 0) {
				tmpDests.add(v);
			}
		}
		if (tmpStart == null) {
			throw new IllegalArgumentException("No valve found with start name: " + startName);
		}
		
		this.allDestinations = Collections.unmodifiableList(tmpDests);
		this.start = tmpStart;
		this.timeLimit = timeLimit;
		return;
	}
	
	public int part2() {
		List<Route> routes = new ArrayList<Route>();
		routes.add(new Route(this.start));
		routes.add(new Route(this.start));
		
		return bestMultipleRoutePressure(routes);
	}
	
	public int bestMultipleRoutePressure(List<Route> routes) {
		if (routes == null || routes.size() < 1) {
			throw new IllegalArgumentException("No routes to compare");
		}
		List<Route> routeList = new ArrayList<Route>(routes);
		Collections.sort(routeList, Comparator.comparing(Route::getTimeUsed));
		Route trialRoute = routeList.get(0);
		int pressure = 0;
		for (Route r : routeList) {
			pressure += this.getRoutePressure(r);
		}
		if (trialRoute.getTimeUsed() < this.timeLimit) {
			int timeLeft = this.timeLimit - trialRoute.getTimeUsed();
			Valve last = trialRoute.getLastStop();
			List<Valve> newDestinations = new ArrayList<Valve>();
			for (Valve v: this.allDestinations) {
				if (v.getFlowRate() > 0 && v.getDistance(last) < timeLeft) {
					newDestinations.add(v);
				}
			}
			for (Route r : routeList) {
				newDestinations.removeAll(r.getCompleteRoute());
			}
			
			if (newDestinations.size() > 0) {
				for (Valve tryNextStep : newDestinations) {
					List<Route> testRouteList = new ArrayList<Route>(routeList);
					testRouteList.set(0, trialRoute.add(tryNextStep));
					int testPressure = this.bestMultipleRoutePressure(testRouteList);
					if (testPressure > pressure) {
						System.out.println("Found new high: " + testPressure);
						pressure = testPressure;
					}
				}
			} else { // else all destinations in range are opened
				
			}
		} // else no more destinations in range
		
		return pressure;
	}
	
	// Assume first element (start) is never opened and all other valves are opened along route
	private int getRoutePressure(Route route) {
		if (!this.pressureCache.containsKey(route)) {
			int pressure = 0;
			int timeRemaining = this.timeLimit;
			List<Valve> steps = route.getCompleteRoute();
			int numSteps = steps.size();
			if (numSteps > 0) {
				Valve prev = steps.get(0);
				for (int i = 1; i < numSteps && timeRemaining > 1; i++) {
					Valve curr = steps.get(i);
					timeRemaining -= curr.getDistance(prev) + 1;
					if (timeRemaining > 0) {
						pressure += timeRemaining * curr.getFlowRate();
					}
					prev = curr;
				}
			}
			this.pressureCache.put(route, Integer.valueOf(pressure));
		}
		return this.pressureCache.get(route).intValue();
	}
}
