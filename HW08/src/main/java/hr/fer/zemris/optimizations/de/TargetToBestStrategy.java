package hr.fer.zemris.optimizations.de;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optimizations.DoubleArraySolution;

public class TargetToBestStrategy extends AbstractStrategy {

    public TargetToBestStrategy(final double F, final Random rnd, final int nLinearCombinations) {
        super(F, rnd, nLinearCombinations);
        // TODO Auto-generated constructor stub
    }

    @Override
    public DoubleArraySolution selectBase(final int targetIndex, final List<DoubleArraySolution> population) {
        DoubleArraySolution best = population.stream().max((e1, e2) -> Double.compare(e1.fitness, e2.fitness))
                .orElse(null);
        DoubleArraySolution xi = population.get(targetIndex).duplicate();

        for (int i = 0; i < xi.values.length; ++i) {
            xi.values[i] += F * (best.values[i] - xi.values[i]);
        }
        return xi;
    }

}
