package io.nsga2.zdt;

import io.nsga2.Problem;
import io.nsga2.Solution;


/**
 * Created by Mateusz Drożdż on 11.11.15.
 */
public class ZDT1 implements Problem<Double> {

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
    	double f1 = x1;
    	return f1;
    }
    
    // funkcja zmiennych decyzyjnych {x2, x3, ..., xn}
    private Double evaluateG(Solution<Double> solution) {
    	double g = 0.0;
    	for (int i = 1; i < solution.getDecisionVariablesNumber(); i++) {
    		g += solution.getVariableValue(i);
    	}
        return (9. / (solution.getDecisionVariablesNumber() - 1)) * g + 1.0;
    }
    
    // zalezna od wartosci f1 i g
    private Double evaluateH(double f1, double g) {
    	double h = 0.0;
    	h = 1.0 - Math.sqrt(f1 / g);
    	return h;
    }
    
    public double getIdealParetoFront(double x) {
    	return 1 - Math.sqrt(x);
    }
}
