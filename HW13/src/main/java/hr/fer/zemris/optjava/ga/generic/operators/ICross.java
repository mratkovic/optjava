package hr.fer.zemris.optjava.ga.generic.operators;

import hr.fer.zemris.optjava.ga.AbstractGASolution;

public interface ICross<T extends AbstractGASolution> {

    public T crossParents(final T p1, T p2);

}
