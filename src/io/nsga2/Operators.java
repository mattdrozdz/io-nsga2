package io.nsga2;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mateusz Drożdż on 12.11.15.
 */
public class Operators {

    private static final double distributionIndex = 2;
    private static final double mutationProbability = 0.4;

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
        return doCrossover(parents.get(0), parents.get(1));
    }

    public static DoubleSolution mutation(DoubleSolution solution) {
        doMutation(mutationProbability, solution);
        return solution;
    }

    private static List<DoubleSolution> doCrossover(DoubleSolution parent1, DoubleSolution parent2) {
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

            if(childX1 > 1) {
            	childX1 = 1;
            }
            
            if(childX1 < 0) {
            	childX1 = 0;
            }
            
            if (childX2 > 1) {
            	childX2 = 1;
            }
            
            if(childX2 < 0) {
            	childX2 = 0;
            }
            child1.setVariableValue(i, Double.valueOf(childX1));
            child2.setVariableValue(i, Double.valueOf(childX2));
        }

        return Arrays.asList(child1, child2);
    }

    /**
     * https://books.google.pl/books?id=OSTn4GSy2uQC&pg=PA124&dq=Polynomial+mutation+algorithm&hl=pl&sa=X&ved=0CB8Q6AEwAGoVChMI3sieuMyLyQIVS9UsCh0YwAwj#v=onepage&q=Polynomial%20mutation%20algorithm&f=false
     */
    private static void doMutation(double probability, DoubleSolution solution) {

        for (int i=0; i<solution.getDecisionVariablesNumber(); i++) {
            Random random = new Random(System.currentTimeMillis());
            if (random.nextDouble() < probability) {
                double y = solution.getVariableValue(i);
                double deltaY = 0.0;
                double mutPow = 1.0 / (distributionIndex + 1.0);
                double r = random.nextDouble();
                if (r < 0.5) {
                    deltaY = Math.pow(2.0 * r, mutPow) - 1.0;
                } else {
					deltaY = 1.0 - Math.pow(2.0 - 2.0 * r, mutPow);
                }

                y += deltaY;
                if (y > 1) {
                	y = 1;
                }
                if (y < 0) {
                	y = 0;
                }
                solution.setVariableValue(i, Double.valueOf(y));
            }
        }

    }
}
