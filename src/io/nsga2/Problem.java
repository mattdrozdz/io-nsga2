package io.nsga2;


/**
 * Created by Mateusz Drożdż on 11.11.15.
 */
public interface Problem<T> {

    void evaluateSolution(Solution<T> solution);
}
