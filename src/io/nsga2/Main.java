package io.nsga2;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import io.nsga2.zdt.ZDT1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mateusz Drożdż on 11.11.15.
 */
public class Main {

    private static int populationSize;
    private static int iterationsNumber;
    private static int attributesNumber;

    public static void main(String[] args) {

        Problem problem = new ZDT1();

        List<DoubleSolution> population = createRandomPopulation(populationSize);
//        TODO: move to main loop???
        for (DoubleSolution solution : population) {
            problem.evaluateSolution(solution);
        }

        for (int iteration=0; iteration < iterationsNumber; iteration++) {
            population = nondominatedSort(population);
            List<DoubleSolution> offspringPopulation = createOffspringPopulation(population);

            List<DoubleSolution> union = new ArrayList<DoubleSolution>();
            union.addAll(population);
            union.addAll(offspringPopulation);

            union = nondominatedSort(union);

            List<DoubleSolution> nextPopulation = new ArrayList<DoubleSolution>();
            int remaining = populationSize;
            int frontId = 0;
            while (remaining > 0) {
                List<DoubleSolution> front = getFront(union, frontId);
                if (front.size() > remaining) {
                    front = crowdingSort(front);
                    nextPopulation.addAll(front.subList(0, remaining));
                    remaining = 0;
                } else {
                    nextPopulation.addAll(front);
                    remaining -= front.size();
                }
            }

            population = nextPopulation;

        }

    }

    private static List<DoubleSolution> createRandomPopulation(int populationSize) {
        List<DoubleSolution> population = new ArrayList<DoubleSolution>();
        for (int i=0; i<populationSize; i++) {
            population.add(createRandomSolution());
        }
        return population;
    }

    private static DoubleSolution createRandomSolution() {
        DoubleSolution solution = new DoubleSolution(attributesNumber);
        Random random = new Random();
        for (int i=0; i<attributesNumber; i++) {
            solution.setAttribute(i, random.nextDouble());
        }
        return solution;
    }

    private static List<DoubleSolution> nondominatedSort(List<DoubleSolution> population) {
        throw new NotImplementedException();
    }

    private static List<DoubleSolution> createOffspringPopulation(List<DoubleSolution> population) {
        throw new NotImplementedException();
    }

    private static List<DoubleSolution> getFront(List<DoubleSolution> union, int frontId) {
        throw new NotImplementedException();
    }

    private static List<DoubleSolution> crowdingSort(List<DoubleSolution> front) {
        throw new NotImplementedException();
    }
}
