package hr.fer.zemris.optjava.moop.distance;

import hr.fer.zemris.optjava.moop.solution.DoubleArraySolution;

public class ObjectiveSpaceDistance implements IDistance<DoubleArraySolution> {

    @Override
    public double calcDist(final DoubleArraySolution xi, final DoubleArraySolution xj) {
        double sum = 0;
        int n = xi.objective.length;
        for (int i = 0; i < n; ++i) {
            sum += Math.pow(xi.objective[i] - xj.objective[i], 2);
        }
        return Math.sqrt(sum);
    }

}
