package hr.fer.zemris.optjava.moop.selection;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.moop.solution.MultiObjectiveSolution;

public class Tournament<T extends MultiObjectiveSolution> implements ISelection<T> {

    private final int n;

    public Tournament(final int n) {
        this.n = n;
    }

    private boolean updateBest(final T candidate, final T best) {
        if (best == null) {
            return true;
        }

        return candidate.fitness > best.fitness;

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
