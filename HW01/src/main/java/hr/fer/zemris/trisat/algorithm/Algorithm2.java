package hr.fer.zemris.trisat.algorithm;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import hr.fer.zemris.trisat.SATFormulaStats;

/**
 * Class that implements simple iterative algorithm that uses number of
 * satisfied clauses as fitness function. Next solution is picked randomly from
 * list of neighbors with maximum score.
 *
 * @author marko
 *
 */
public class Algorithm2 extends AbstractIterativeAlgorithm {

    @Override
    protected double getFitness(final SATFormulaStats satStats) {
        return satStats.getNumberOfSatisfied();
    }

    @Override
    protected SolutonCandidate pickNextSolution(final List<SolutonCandidate> neighborhood, final Random rnd) {
        double maxFitness = neighborhood.stream().mapToDouble(e -> e.getFitness()).max().getAsDouble();
        // filter solutions with max fitness
        List<SolutonCandidate> bestNeighbors = neighborhood.stream().filter(s -> s.getFitness() == maxFitness)
                .collect(Collectors.toList());
        // randomly select one
        return bestNeighbors.get(rnd.nextInt(bestNeighbors.size()));
    }

    @Override
    protected boolean exitCondition(final SolutonCandidate oldSolution, final SolutonCandidate newSolution) {
        boolean exit = oldSolution.getFitness() > newSolution.getFitness();
        if (exit) {
            System.out.println("Stuck in local optima");
        }
        return exit;
    }

}
