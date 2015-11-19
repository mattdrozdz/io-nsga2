package io.nsga2.sorting;

import io.nsga2.DoubleSolution;
import io.nsga2.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrowdSort {

	public void sort(List<DoubleSolution> solutionList) {
		Collections.sort(solutionList, new DensityComparator());
	}
	
	/**
	 * Assigns crowding distances to all solutions.
	 */
	public void computeDensityEstimator(List<DoubleSolution> solutionList) {
		int size = solutionList.size();

		if (size == 0) {
			return;
		}

		if (size == 1) {
			solutionList.get(0).setDistance(Double.POSITIVE_INFINITY);
			return;
		}

		if (size == 2) {
			solutionList.get(0).setDistance(Double.POSITIVE_INFINITY);
			solutionList.get(1).setDistance(Double.POSITIVE_INFINITY);

			return;
		}

		List<Solution<Double>> front = new ArrayList<>(size);
		for (Solution<Double> solution : solutionList) {
			front.add(solution);
		}

		for (int i = 0; i < size; i++) {
			front.get(i).setDistance(0.0);
		}

		double objectiveMaxn;
		double objectiveMinn;
		double distance;

		int numberOfObjectives = solutionList.get(0).getObjectivesNumber();

		for (int i = 0; i < numberOfObjectives; i++) {
			Collections.sort(front, new ObjectiveComparator(i));
			objectiveMinn = front.get(0).getObjective(i);
			objectiveMaxn = front.get(front.size() - 1).getObjective(i);

			front.get(0).setDistance(Double.POSITIVE_INFINITY);
			front.get(size - 1).setDistance(Double.POSITIVE_INFINITY);

			for (int j = 1; j < size - 1; j++) {
				distance = front.get(j + 1).getObjective(i) - front.get(j - 1).getObjective(i);
				distance = distance / (objectiveMaxn - objectiveMinn);
				distance += (double) front.get(j).getDistance();
				front.get(j).setDistance(distance);
			}
		}
	}
}