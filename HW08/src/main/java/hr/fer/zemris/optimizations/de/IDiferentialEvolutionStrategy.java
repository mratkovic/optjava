package hr.fer.zemris.optimizations.de;

import java.util.List;

import hr.fer.zemris.optimizations.DoubleArraySolution;

public interface IDiferentialEvolutionStrategy {
    DoubleArraySolution selectBase(final int targetIndex, List<DoubleArraySolution> population);

    DoubleArraySolution mutate(final int targetIndex, List<DoubleArraySolution> population);
}