package hr.fer.zemris.optjava.moop.selection;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.moop.solution.MultiObjectiveSolution;

public class CrowdedTournament<T extends MultiObjectiveSolution> implements ISelection<T> {

    private final int n;

    public CrowdedTournament(final int n) {
        this.n = n;
    }

    private boolean updateBest(final T candidate, final T best) {
        if (best == null) {
            return true;
        }
        if (candidate.rank != best.rank) {
            return candidate.rank < best.rank;
        }
        return candidate.distance > best.distance;

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
