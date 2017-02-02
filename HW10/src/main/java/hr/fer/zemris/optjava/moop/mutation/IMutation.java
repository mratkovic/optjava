package hr.fer.zemris.optjava.moop.mutation;

import hr.fer.zemris.optjava.moop.solution.MultiObjectiveSolution;

public interface IMutation<T extends MultiObjectiveSolution> {
    public T mutate(T individual);

}
