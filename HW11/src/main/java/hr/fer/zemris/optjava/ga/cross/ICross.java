package hr.fer.zemris.optjava.ga.cross;

import hr.fer.zemris.optjava.ga.solution.GASolution;

public interface ICross<T extends GASolution<int[]>> {

    public T crossParents(final T p1, T p2);

}
