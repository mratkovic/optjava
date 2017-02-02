package hr.fer.zemris.optjava.ga.selection;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.ga.solution.SingleObjectiveSolution;

public interface ISelection<T extends SingleObjectiveSolution> {
    public T selectFromPopulation(final List<T> population, final Random rnd);
}
