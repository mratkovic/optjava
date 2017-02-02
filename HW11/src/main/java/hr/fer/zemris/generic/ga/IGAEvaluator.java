package hr.fer.zemris.generic.ga;

import hr.fer.zemris.optjava.ga.solution.GASolution;

public interface IGAEvaluator<T> {
    public void evaluate(GASolution<T> p);
}