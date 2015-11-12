package io.nsga2;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

/**
 * Created by Mateusz Drożdż on 12.11.15.
 */
public class Operators {

    private static final double distributionIndex = 2;
    private static final double crossoverProbability = 0.5;

    /**
     * @return two randomly selected solutions
     */
    public static List<DoubleSolution> selection(List<DoubleSolution> solutions) {
        List<DoubleSolution> copy = new LinkedList<>(solutions);
        Collections.shuffle(copy);
        return copy.subList(0, 2);
    }

    /**
     * @return two children solutions using SBX operator (@see http://www.iitk.ac.in/kangal/resources.shtml)
     */
    public static List<DoubleSolution> crossover(List<DoubleSolution> parents) {
        if (parents.size() != 2) {
            throw new IllegalStateException("There must be two parents instead of " + parents.size());
        }
        throw new NotImplementedException();
    }

    public static DoubleSolution mutation(DoubleSolution solution) {
        throw new NotImplementedException();
    }

    private static List<DoubleSolution> doCrossover(double probability, DoubleSolution parent1, DoubleSolution parent2) {
        if (parent1.getDecisionVariablesNumber() != parent2.getDecisionVariablesNumber()) {
            throw new IllegalStateException("Parents with different DecisionVariablesNumber");
        }
        DoubleSolution child1 = new DoubleSolution(parent1.getDecisionVariablesNumber(), parent1.getObjectivesNumber());
        DoubleSolution child2 = new DoubleSolution(parent1.getDecisionVariablesNumber(), parent1.getObjectivesNumber());
        Random random = new Random(System.currentTimeMillis());

        for (int i=0; i<parent1.getDecisionVariablesNumber(); i++) {
            double x1 = parent1.getVariableValue(i);
            double x2 = parent2.getVariableValue(i);
//          step 1
            double u = random.nextDouble();
//          step 2
            double beta = 0;
            if (u < 0.5) {
                beta = Math.pow(2.0*u, 1/(distributionIndex + 1));
            } else if (u < 0.5) {
                beta = Math.pow(0.5 / (1.0 - u), 1.0 / (distributionIndex + 1));
            } else if (u == 0.5) {
                beta = 1.0;
            }
//          step 3
            double childX1 = 0.5 * ((1.0 + beta) * x1 + (1 - beta) * x2);
            double childX2 = 0.5 * ((1.0 - beta) * x1 + (1.0 + beta) * x2);

            child1.setVariableValue(i, childX1);
            child2.setVariableValue(i, childX2);
        }

        return Arrays.asList(child1, child2);
    }
}
