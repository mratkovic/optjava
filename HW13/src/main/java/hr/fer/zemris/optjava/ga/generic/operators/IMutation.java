package hr.fer.zemris.optjava.ga.generic.operators;

import hr.fer.zemris.optjava.ga.AbstractGASolution;

public interface IMutation<T extends AbstractGASolution> {
    public T mutate(T individual);

}
