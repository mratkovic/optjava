package hr.fer.zemris.optjava.ga.generic.operators;

import java.util.List;

public interface IPopulationInitializer<T> {

    List<T> initialize(int popSize);
}
