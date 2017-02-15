package hr.fer.zemris.optjava.ga.generic.operators;

import java.util.List;

import hr.fer.zemris.optjava.ga.AbstractGASolution;

public interface ISelection<T extends AbstractGASolution> {
    public T selectFromPopulation(final List<T> population);
}
