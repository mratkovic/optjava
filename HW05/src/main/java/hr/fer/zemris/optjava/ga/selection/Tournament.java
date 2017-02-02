package hr.fer.zemris.optjava.ga.selection;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.ga.solution.SingleObjectiveSolution;

public class Tournament<T extends SingleObjectiveSolution> implements ISelection<T> {

    private final int n;
    private final boolean pickBest;

    Random rnd;

    public Tournament(final int n, final boolean pickBest, final Random rnd) {
        this.n = n;
        this.pickBest = pickBest;
        this.rnd = rnd;
    }

    public Tournament(final int n, final Random rnd) {
        super();
        this.n = n;
        this.pickBest = true;
        this.rnd = rnd;
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
        T best = null;
        for (int i = 0; i < n; ++i) {
            T candidate = population.get(rnd.nextInt(population.size()));
            if (updateBest(candidate, best)) {
                best = candidate;
            }
        }
        return best;
    }

}
