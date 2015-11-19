package io.nsga2;

import io.nsga2.sorting.CrowdSort;
import io.nsga2.sorting.DominanceSorter;
import io.nsga2.zdt.ZDT1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Mateusz Drożdż on 11.11.15.
 */
public class NGSA_II {

	private static int populationSize = 1000;
	private static int iterationsNumber = 1000;

	private static int objectivesNumber = 2;
	private static int variablesNumber = 30;

	public static void main(String[] args) {

		Problem<Double> problem = new ZDT1();

		List<DoubleSolution> population = createRandomPopulation(populationSize);
		for (DoubleSolution solution : population) {
			problem.evaluateSolution(solution);
		}

		for (int iteration = 0; iteration < iterationsNumber; iteration++) {
			population = nondominatedSort(population);
			List<DoubleSolution> offspringPopulation = createOffspringPopulation(population);
			for (DoubleSolution solution : offspringPopulation) {
				problem.evaluateSolution(solution);
			}

			List<DoubleSolution> union = new ArrayList<DoubleSolution>();
			union.addAll(population);
			union.addAll(offspringPopulation);

			union = nondominatedSort(union);

			List<DoubleSolution> nextPopulation = new ArrayList<DoubleSolution>();
			int remaining = populationSize;
			int frontId = 0;
			while (remaining > 0) {
				List<DoubleSolution> front = getFront(union, frontId);
				if (front.isEmpty()) {
					break;
				}

				if (front.size() > remaining) {
					front = crowdingSort(front);
					nextPopulation.addAll(front.subList(0, remaining));
					remaining = 0;
				} else {
					nextPopulation.addAll(front);
					remaining -= front.size();
				}
				frontId++;
			}

			population = nextPopulation;

		}

		for (DoubleSolution doubleSolution : population) {
			System.out.println(doubleSolution);
		}
	}

	private static List<DoubleSolution> createRandomPopulation(int populationSize) {
		List<DoubleSolution> population = new ArrayList<DoubleSolution>();
		for (int i = 0; i < populationSize; i++) {
			population.add(createRandomSolution());
		}
		return population;
	}

	private static DoubleSolution createRandomSolution() {
		DoubleSolution solution = new DoubleSolution(variablesNumber, objectivesNumber);
		Random random = new Random();
		for (int i = 0; i < variablesNumber; i++) {
			solution.setVariableValue(i, random.nextDouble());
		}
		solution.setVariableValue(0, random.nextDouble());
		return solution;
	}

	private static List<DoubleSolution> nondominatedSort(List<DoubleSolution> population) {
		DominanceSorter sorter = new DominanceSorter();
		List<ArrayList<DoubleSolution>> sort = sorter.sort(population);
		List<DoubleSolution> sortedPopulation = new ArrayList<DoubleSolution>();
		for (ArrayList<DoubleSolution> arrayList : sort) {
			sortedPopulation.addAll(arrayList);
		}
		return sortedPopulation;
	}

	private static List<DoubleSolution> createOffspringPopulation(List<DoubleSolution> population) {
		List<DoubleSolution> offspringPopulation = new ArrayList<>(population.size());
		for (int i = 0; i < (populationSize / 2); i++) {
			List<DoubleSolution> parents = Operators.selection(population);
			List<DoubleSolution> offspring = Operators.crossover(parents);
			offspring.forEach(Operators::mutation);
			offspringPopulation.addAll(offspring);
		}
		return offspringPopulation;
	}

	private static List<DoubleSolution> getFront(List<DoubleSolution> union, int frontId) {
		return union.stream().filter(solution -> solution.getFront() == frontId).collect(Collectors.toList());
	}

	private static List<DoubleSolution> crowdingSort(List<DoubleSolution> front) {
		CrowdSort cs = new CrowdSort();
		cs.computeDensityEstimator(front);
		cs.sort(front);
		return front;
	}
}
