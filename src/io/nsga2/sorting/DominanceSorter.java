package io.nsga2.sorting;

import io.nsga2.DoubleSolution;
import io.nsga2.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DominanceSorter {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ArrayList<DoubleSolution>> sort(List<DoubleSolution> solutionSet) {
		Comparator<Solution> dominanceComparator = new DominanceComparator();
		List<DoubleSolution> population = solutionSet;

		// dominateMe[i] contains the number of solutions dominating i
		// iDominate[k] contains the list of solutions dominated by k
		int[] dominateMe = new int[population.size()];
		List<Integer>[] iDominate = new List[population.size()];

		for (int p = 0; p < population.size(); p++) {
			iDominate[p] = new LinkedList<>();
			dominateMe[p] = 0;
		}

		// front[i] contains the list of individuals belonging to the front i
		List<Integer>[] front = new List[population.size() + 1];

		for (int i = 0; i < front.length; i++) {
			front[i] = new LinkedList<>();
		}

		int flagDominate;
		// For all q individuals , calculate if p dominates q or vice versa
		for (int p = 0; p < (population.size() - 1); p++) {
			for (int q = p + 1; q < population.size(); q++) {
				flagDominate = dominanceComparator.compare(solutionSet.get(p), solutionSet.get(q));
				if (flagDominate == -1) {
					iDominate[p].add(q);
					dominateMe[q]++;
				} else if (flagDominate == 1) {
					iDominate[q].add(p);
					dominateMe[p]++;
				}
			}
		}

		for (int i = 0; i < population.size(); i++) {
			if (dominateMe[i] == 0) {
				front[0].add(i);
			}
		}

		// Obtain the rest of fronts
		int i = 0;
		Iterator<Integer> it1, it2; // Iterators
		while (front[i].size() != 0) {
			i++;
			it1 = front[i - 1].iterator();
			while (it1.hasNext()) {
				it2 = iDominate[it1.next()].iterator();
				while (it2.hasNext()) {
					int index = it2.next();
					dominateMe[index]--;
					if (dominateMe[index] == 0) {
						front[i].add(index);
					}
				}
			}
		}

		List<ArrayList<DoubleSolution>> rankedSubpopulations = new ArrayList<>();
		// 0,1,2,....,i-1 are fronts, then i fronts
		for (int j = 0; j < i; j++) {
			rankedSubpopulations.add(j, new ArrayList<DoubleSolution>(front[j].size()));
			it1 = front[j].iterator();
			while (it1.hasNext()) {
				rankedSubpopulations.get(j).add(solutionSet.get(it1.next()));
			}
		}

		return rankedSubpopulations;
	}
}
