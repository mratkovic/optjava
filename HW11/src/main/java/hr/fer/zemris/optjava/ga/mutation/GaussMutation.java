package hr.fer.zemris.optjava.ga.mutation;

import hr.fer.zemris.optjava.ga.solution.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class GaussMutation<T extends GASolution<int[]>> implements IMutation<T> {
    IRNG rnd;

    int[] bounds;
    double chance;
    double sigma;

    public GaussMutation(final int[] bounds, final double sigma, final double chance) {
        super();
        this.bounds = bounds;
        this.chance = chance;
        this.sigma = sigma;
    }

    @Override
    public T mutate(final T individual) {
        if (rnd == null) {
            rnd = RNG.getRNG();
        }
        T child = (T) individual.duplicate();

        for (int i = 0; i < child.data.length; ++i) {
            if (rnd.nextDouble() < chance) {
                child.data[i] += rnd.nextGaussian() * sigma;

                child.data[i] = Math.max(child.data[i], 0);
                child.data[i] = Math.min(child.data[i], bounds[i % bounds.length]);
            }

        }
        return child;
    }
}
