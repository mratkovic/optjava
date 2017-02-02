package hr.fer.zemris.optjava.ga.selection;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.ga.solution.SingleObjectiveSolution;

public class Tournament<T extends SingleObjectiveSolution> implements ISelection<T> {

    private final int n;
    private final boolean pickBest;

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
    public T selectFromPopulation(final List<T> population, final Random rnd) {
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
