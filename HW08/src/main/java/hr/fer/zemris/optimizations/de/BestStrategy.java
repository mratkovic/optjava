package hr.fer.zemris.optimizations.de;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optimizations.DoubleArraySolution;

public class BestStrategy extends AbstractStrategy {

    public BestStrategy(final double F, final Random rnd, final int nLinearCombinations) {
        super(F, rnd, nLinearCombinations);
    }

    @Override
    public DoubleArraySolution selectBase(final int targetIndex, final List<DoubleArraySolution> population) {
        DoubleArraySolution best = population.stream().max((e1, e2) -> Double.compare(e1.fitness, e2.fitness))
                .orElse(null);
        return best;
    }

}
