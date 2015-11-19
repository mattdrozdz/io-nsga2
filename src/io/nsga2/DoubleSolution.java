package io.nsga2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz Drożdż on 11.11.15.
 */
public class DoubleSolution implements Solution<Double> {
	private final List<Double> decisionVariables;
	private final int objectivesNumber;
	private final Double[] objectives;

	private int front;
	private Double distance;

	public DoubleSolution(int variablesNumber, int objectivesNumber) {
		this.decisionVariables = new ArrayList<Double>(variablesNumber);
		for (int i = 0; i < variablesNumber; i++) {
			decisionVariables.add(0.0);
		}
		this.objectivesNumber = objectivesNumber;
		this.objectives = new Double[objectivesNumber];
	}

	@Override
	public void setVariableValue(int index, Double value) {
		decisionVariables.set(index, value);
	}

	@Override
	public Double getVariableValue(int index) {
		return decisionVariables.get(index);
	}

	@Override
	public void setObjective(int index, Double value) {
		objectives[index] = value;
	}

	@Override
	public Double getObjective(int index) {
		return objectives[index];
	}

	@Override
	public void setFront(int rank) {
		this.front = rank;
	}

	@Override
	public int getFront() {
		return front;
	}

	@Override
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public Double getDistance() {
		return distance;
	}

	@Override
	public int getDecisionVariablesNumber() {
		return decisionVariables.size();
	}

	@Override
	public int getObjectivesNumber() {
		return objectivesNumber;
	}

	@Override
	public String toString() {
		return objectives[0].toString() + "; " + objectives[1].toString();
	}
}
