package hr.fer.zemris.optjava.ga.selection;

import java.util.List;

import hr.fer.zemris.optjava.ga.solution.GASolution;

public interface ISelection<T extends GASolution<int[]>> {
    public T selectFromPopulation(final List<T> population);
}
