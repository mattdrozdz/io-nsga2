package io.nsga2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz Drożdż on 11.11.15.
 */
public class DoubleSolution implements Solution<Double> {
    private final List<Double> attributes;
    private final int objectivesNumber;
    private final Double[] objectives;

    private int rank;
    private Double distance;

    public DoubleSolution(int objectivesNumber) {
        this.attributes = new ArrayList<Double>();
        this.objectivesNumber = objectivesNumber;
        this.objectives = new Double[objectivesNumber];
    }

    @Override
    public void setAttribute(int index, Double value) {
        attributes.set(index, value);
    }

    @Override
    public Double getAttribute(int index) {
        return attributes.get(index);
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
    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int getRank() {
        return rank;
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
		return attributes.size();
	}

	@Override
	public int getObjectivesNumber() {
		return objectivesNumber;
	}
}
