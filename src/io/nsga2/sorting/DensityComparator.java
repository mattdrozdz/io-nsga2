package io.nsga2.sorting;

import io.nsga2.Solution;

import java.util.Comparator;

public class DensityComparator implements Comparator<Solution<Double>> {

	@Override
	public int compare(Solution<Double> o1, Solution<Double> o2) {
		Double distance1 = o1.getDistance();
		Double distance2 = o2.getDistance();
		
		return Double.compare(distance1, distance2);
	}

}
