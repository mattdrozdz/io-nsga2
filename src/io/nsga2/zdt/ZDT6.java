package io.nsga2.zdt;

import io.nsga2.Problem;
import io.nsga2.Solution;


public class ZDT6 implements Problem<Double> {

	@Override
    public void evaluateSolution(Solution<Double> solution) {
	    double[] f = new double[solution.getObjectivesNumber()];
	    f[0] = evaluateF1(solution.getVariableValue(0));
	    double g = evaluateG(solution);
	    double h = evaluateH(f[0], g);
	    f[1] = h * g;

	    solution.setObjective(0, f[0]);
	    solution.setObjective(1, f[1]);
    }
    
    // funkcja jednej (pierwszej) zmiennej decyzyjnej
    private Double evaluateF1(double x1) {
    	double f1 = 1 - Math.exp(-4 * x1)*Math.pow(Math.sin(6*Math.PI*x1), 6);
    	return f1;
    }
    
    // funkcja zmiennych decyzyjnych {x2, x3, ..., xn}
    private Double evaluateG(Solution<Double> solution) {
    	double sum = 0;
    	for (int i = 1; i < solution.getDecisionVariablesNumber(); i++) {
    		sum += solution.getVariableValue(i);
    	}
    	double g = 1 + 9 * Math.pow(sum / (solution.getDecisionVariablesNumber() - 1), 0.25);
    	return g;
    }
    
    // zalezna od wartosci f1 i g
    private Double evaluateH(double f1, double g) {
    	double h = 0.0;
    	h = 1.0 - Math.pow(f1 / g, 2);
    	return h;
    }
}
