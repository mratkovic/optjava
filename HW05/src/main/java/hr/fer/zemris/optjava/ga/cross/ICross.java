package hr.fer.zemris.optjava.ga.cross;

import hr.fer.zemris.optjava.ga.solution.SingleObjectiveSolution;

public interface ICross<T extends SingleObjectiveSolution> {

    public T crossParents(final T p1, T p2);

}
