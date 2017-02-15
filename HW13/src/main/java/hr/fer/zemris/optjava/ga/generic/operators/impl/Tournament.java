package hr.fer.zemris.optjava.ga.generic.operators.impl;

import java.util.List;

import hr.fer.zemris.optjava.ga.AbstractGASolution;
import hr.fer.zemris.optjava.ga.generic.operators.ISelection;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class Tournament<T extends AbstractGASolution> implements ISelection<T> {

    private final int n;
    IRNG rnd;

    public Tournament(final int n, final IRNG rnd) {
        super();
        this.n = n;
        this.rnd = rnd;
    }

    public Tournament(final int n) {
        super();
        this.n = n;
    }

    @Override
    public T selectFromPopulation(final List<T> population) {
        if (rnd == null) {
            rnd = RNG.getRNG();
        }
        T best = null;
        for (int i = 0; i < n; ++i) {
            T candidate = population.get(rnd.nextInt(0, population.size()));
            if (best == null || candidate.fitness > best.fitness) {
                best = candidate;
            }
        }
        return best;
    }

}
