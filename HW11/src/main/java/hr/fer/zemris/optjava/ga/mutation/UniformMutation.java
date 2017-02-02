package hr.fer.zemris.optjava.ga.mutation;

import hr.fer.zemris.optjava.ga.solution.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class UniformMutation<T extends GASolution<int[]>> implements IMutation<T> {
    IRNG rnd;

    int[] bounds;
    int lo, hi;
    double chance;

    public UniformMutation(final int[] bounds, final int lo, final int hi, final double chance) {
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
                child.data[i] += rnd.nextInt(lo, hi);

                child.data[i] = Math.max(child.data[i], 0);
                child.data[i] = Math.min(child.data[i], bounds[i % bounds.length]);
            }

        }
        return child;
    }
}
