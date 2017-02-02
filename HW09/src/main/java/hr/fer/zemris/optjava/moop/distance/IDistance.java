package hr.fer.zemris.optjava.moop.distance;

import hr.fer.zemris.optjava.moop.solution.DoubleArraySolution;

public interface IDistance<T extends DoubleArraySolution> {

    public double calcDist(T xi, T xj);

}
