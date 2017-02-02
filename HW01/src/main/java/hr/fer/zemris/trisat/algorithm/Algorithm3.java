package hr.fer.zemris.trisat.algorithm;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.trisat.SATFormulaStats;

/**
 * Implementation of third algorithm.
 *
 * @author marko
 *
 */
public class Algorithm3 extends AbstractIterativeAlgorithm {
    private final int numberOfBest = 2;

    @Override
    protected double getFitness(final SATFormulaStats satStats) {
        return satStats.getPercentageBonus();
    }

    @Override
    protected SolutonCandidate pickNextSolution(final List<SolutonCandidate> neighborhood, final Random rnd) {
        // sort desc by fitness
        Collections.sort(neighborhood, Collections.reverseOrder());
        // return randomly selected from top n best
        return neighborhood.get(new Random().nextInt(numberOfBest));
    }

    @Override
    protected boolean exitCondition(final SolutonCandidate oldSolution, final SolutonCandidate newSolution) {
        // no exit condition
        return false;
    }

}
