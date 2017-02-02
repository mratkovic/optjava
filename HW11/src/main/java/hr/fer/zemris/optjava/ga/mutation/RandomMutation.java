package hr.fer.zemris.optjava.ga.mutation;

import hr.fer.zemris.optjava.ga.solution.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class RandomMutation<T extends GASolution<int[]>> implements IMutation<T> {
    IRNG rnd;

    int[] bounds;
    int lo, hi;
    double chance;

    public RandomMutation(final int[] bounds, final int lo, final int hi, final double chance) {
        super();
        this.bounds = bounds;
        this.lo = lo;
        this.hi = hi;
        this.chance = chance;
    }

    @Override
    public T mutate(final T individual) {
        if (rnd == null) {
            rnd = RNG.getRNG();
        }
        T child = (T) individual.duplicate();

        for (int i = 0; i < child.data.length; ++i) {
            if (rnd.nextDouble() < chance) {
                child.data[i] = rnd.nextInt(0, bounds[i % bounds.length]);
            }

        }
        return child;
    }
}
