package hr.fer.zemris.optimizations.de;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optimizations.DoubleArraySolution;

public class RandStrategy extends AbstractStrategy {

    public RandStrategy(final double F, final Random rnd, final int nLinearCombinations) {
        super(F, rnd, nLinearCombinations);
    }

    @Override
    public DoubleArraySolution selectBase(final int targetIndex, final List<DoubleArraySolution> population) {
        int baseIndex = targetIndex;
        while (baseIndex != targetIndex) {
            baseIndex = rnd.nextInt(population.size());
        }

        return population.get(baseIndex);
    }

}
