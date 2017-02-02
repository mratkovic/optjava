package hr.fer.zemris.optjava.moop.selection;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.moop.solution.MultiObjectiveSolution;

public interface ISelection<T extends MultiObjectiveSolution> {
    public T selectFromPopulation(final List<T> population, final Random rnd);
}
