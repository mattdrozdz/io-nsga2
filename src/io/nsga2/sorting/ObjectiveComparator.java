package io.nsga2.sorting;

import io.nsga2.Solution;

import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class ObjectiveComparator implements Comparator<Solution> {

	private int objectiveId;

	public ObjectiveComparator(int objectiveId) {
		this.objectiveId = objectiveId;
	}

	@Override
	public int compare(Solution solution1, Solution solution2) {
		int result;
		if (solution1 == null) {
			if (solution2 == null) {
				result = 0;
			} else {
				result = 1;
			}
		} else if (solution2 == null) {
			result = -1;
		} else if (solution1.getObjectivesNumber() <= objectiveId || solution2.getObjectivesNumber() <= objectiveId) {
			throw new RuntimeException("One of solutions has wrong number of objectives");
		} else {
			Double objective1 = solution1.getObjective(this.objectiveId);
			Double objective2 = solution2.getObjective(this.objectiveId);
			result = Double.compare(objective1, objective2);
		}
		return result;
	}
}