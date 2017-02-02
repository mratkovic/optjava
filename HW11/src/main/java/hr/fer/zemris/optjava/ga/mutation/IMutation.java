package hr.fer.zemris.optjava.ga.mutation;

import hr.fer.zemris.optjava.ga.solution.GASolution;

public interface IMutation<T extends GASolution<int[]>> {
    public T mutate(T individual);

}
