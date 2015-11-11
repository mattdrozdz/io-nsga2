package io.nsga2;

/**
 * Created by Mateusz Drożdż on 11.11.15.
 */
public interface Solution<T> {
    void setAttribute(int index, T value);
    T getAttribute(int index);
    void setObjective(int index, Double value);
    Double getObjective(int index);

    void setRank(int rank);
    int getRank();
    void setDistance(Double distance);
    Double getDistance();
}
