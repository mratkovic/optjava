package hr.fer.zemris.optimizations.de;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optimizations.DoubleArraySolution;

public class RandToBestStrategy extends AbstractStrategy {

    public RandToBestStrategy(final double F, final Random rnd, final int nLinearCombinations) {
        super(F, rnd, nLinearCombinations - 1);
    }

    @Override
    public DoubleArraySolution selectBase(final int targetIndex, final List<DoubleArraySolution> population) {
        DoubleArraySolution best = population.stream().max((e1, e2) -> Double.compare(e1.fitness, e2.fitness))
                .orElse(null);

        int r0 = targetIndex;
        while (r0 != targetIndex) {
            r0 = rnd.nextInt(population.size());
        }
        DoubleArraySolution xr0 = population.get(r0);
        DoubleArraySolution ret = population.get(targetIndex).duplicate();

        for (int i = 0; i < xr0.values.length; ++i) {
            ret.values[i] = F * (best.values[i] - xr0.values[i]);
        }

        return ret;
    }

}
