package io.nsga2.sorting;

import io.nsga2.Solution;

import java.util.Comparator;

@SuppressWarnings({ "rawtypes" })
public class DominanceComparator implements Comparator<Solution> {

	private double epsilon = 0.01;

	/**
	 * Compares two solutions.
	 *
	 * @return -1 if solution1 dominates solution2,
	 * 		    0 if both are non-dominated,
	 *          1 if solution1 is dominated by solution2
	 */
	@Override
	public int compare(Solution solution1, Solution solution2) {
		boolean solution1Dominates = false;
		boolean solution2Dominates = false;

		int flag;
		double value1, value2;
		for (int i = 0; i < solution1.getObjectivesNumber(); i++) {
			value1 = solution1.getObjective(i);
			value2 = solution2.getObjective(i);
			// wedlug definicji 1.2
			if (value1 / (1 + epsilon) < value2) {
				flag = -1;
			} else if (value2 / (1 + epsilon) < value1) {
				flag = 1;
			} else {
				flag = 0;
			}

			if (flag == -1) {
				solution1Dominates = true;
			}

			if (flag == 1) {
				solution2Dominates = true;
			}
		}

		if (solution1Dominates == solution2Dominates) {
			return 0;
		} else if (solution1Dominates) {
			return -1;
		} else {
			return 1;
		}
	}

}