package hr.fer.zemris.optjava.moop.distance;

import hr.fer.zemris.optjava.moop.solution.DoubleArraySolution;

public class DecisionSpaceDistance implements IDistance<DoubleArraySolution> {

    @Override
    public double calcDist(final DoubleArraySolution xi, final DoubleArraySolution xj) {
        double sum = 0;
        int n = xi.values.length;
        for (int i = 0; i < n; ++i) {
            sum += Math.pow(xi.values[i] - xj.values[i], 2);
        }
        return Math.sqrt(sum);
    }

}
