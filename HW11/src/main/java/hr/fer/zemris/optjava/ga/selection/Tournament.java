package hr.fer.zemris.optjava.ga.selection;

import java.util.List;

import hr.fer.zemris.optjava.ga.solution.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class Tournament<T extends GASolution<int[]>> implements ISelection<T> {

    private final int n;
    private final boolean pickBest;
    IRNG rnd;

    public Tournament(final int n, final boolean pickBest, final IRNG rnd) {
        super();
        this.n = n;
        this.pickBest = pickBest;
        this.rnd = rnd;
    }

    public Tournament(final int n, final boolean pickBest) {
        this.n = n;
        this.pickBest = pickBest;
    }

    public Tournament(final int n) {
        super();
        this.n = n;
        this.pickBest = true;
    }

    private boolean updateBest(final T candidate, final T best) {
        if (best == null) {
            return true;
        }

        if (pickBest) {
            return candidate.fitness > best.fitness;
        } else {
            return candidate.fitness < best.fitness;
        }

    }

    @Override
    public T selectFromPopulation(final List<T> population) {
        if (rnd == null) {
            rnd = RNG.getRNG();
        }
        T best = null;
        for (int i = 0; i < n; ++i) {
            T candidate = population.get(rnd.nextInt(0, population.size()));
            if (updateBest(candidate, best)) {
                best = candidate;
            }
        }
        return best;
    }

}
