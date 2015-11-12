package io.nsga2;


/**
 * Created by Mateusz Drożdż on 11.11.15.
 */
public interface Solution<T> {
    void setVariableValue(int index, T value);
    T getVariableValue(int index);
    int getDecisionVariablesNumber();
    
    void setObjective(int index, Double value);
    Double getObjective(int index);
    int getObjectivesNumber();

    void setFront(int rank);
    int getFront();
    void setDistance(Double distance);
    Double getDistance();
}
