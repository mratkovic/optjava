package hr.fer.zemris.optjava.ga.mutation;

import hr.fer.zemris.optjava.ga.solution.SingleObjectiveSolution;

public interface IMutation<T extends SingleObjectiveSolution> {
    public T mutate(T individual);

}
