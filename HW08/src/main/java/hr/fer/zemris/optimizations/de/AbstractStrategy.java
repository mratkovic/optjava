package hr.fer.zemris.optimizations.de;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optimizations.DoubleArraySolution;

public abstract class AbstractStrategy implements IDiferentialEvolutionStrategy {
    Random rnd;
    int nLinearCombinations;
    double F;

    public AbstractStrategy(final double F, final Random rnd, final int nLinearCombinations) {
        super();
        this.F = F;
        this.rnd = rnd;
        this.nLinearCombinations = nLinearCombinations;
    }

    @Override
    public DoubleArraySolution mutate(final int targetIndex, final List<DoubleArraySolution> population) {
        DoubleArraySolution mutant = selectBase(targetIndex, population).duplicate();

        List<Integer> intList = new LinkedList<>();
        for (int i = 0; i < population.size(); ++i) {
            intList.add(i);
        }
        intList.remove(Integer.valueOf(targetIndex));

        for (int i = 0; i < nLinearCombinations; ++i) {
            int r1 = intList.get(rnd.nextInt(intList.size()));
            intList.remove(Integer.valueOf(r1));

            int r2 = intList.get(rnd.nextInt(intList.size()));
            intList.remove(Integer.valueOf(r2));

            double[] xr1 = population.get(r1).values;
            double[] xr2 = population.get(r2).values;
            for (int j = 0; j < mutant.values.length; ++j) {
                mutant.values[j] += F * (xr2[j] - xr1[j]);
            }
        }

        return mutant;
    }
}
