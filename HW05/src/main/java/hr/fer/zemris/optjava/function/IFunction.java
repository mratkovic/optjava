package hr.fer.zemris.optjava.function;

import hr.fer.zemris.optjava.ga.solution.SingleObjectiveSolution;

public interface IFunction<T extends SingleObjectiveSolution> {

    public double valueAt(T sol);

}
