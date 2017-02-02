package hr.fer.zemris.trisat.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

/**
 * Abstract class that models any iterative algorithm for finding solution for
 * given {@link SATFormula}.
 *
 * @author marko
 *
 */
public abstract class AbstractIterativeAlgorithm implements IAlgorithm {

    protected final int maxNumberOfIteration = 100000;

    @Override
    public void solve(final SATFormula satFormula) {
        Random rnd = new Random();
        BitVector initial = new BitVector(rnd, satFormula.getNumberOfVariables());
        SolutonCandidate solution = new SolutonCandidate(initial, 0.0);
        SATFormulaStats satStats = new SATFormulaStats(satFormula);

        int it = 0;
        while (it < maxNumberOfIteration) {
            it += 1;

            satStats.setAssignment(solution.getBitVector(), true);
            solution.setFitness(getFitness(satStats));

            if (satStats.getNumberOfSatisfied() == satFormula.getNumberOfClauses()) {
                System.out.println("Solution found in iteration " + it);
                System.out.println(solution.getBitVector());
                return;
            }

            // expand
            List<SolutonCandidate> neighbors = evaluateNeighborhood(satFormula, satStats,
                    new BitVectorNGenerator(solution.getBitVector()));

            SolutonCandidate newSolution = pickNextSolution(neighbors, rnd);
            if (exitCondition(solution, newSolution)) {
                return;
            }

            solution = newSolution;

        }

        System.out.println("Maximal number of iterations reached");
    }

    private List<SolutonCandidate> evaluateNeighborhood(final SATFormula satFormula, final SATFormulaStats satStats,
            final BitVectorNGenerator gen) {

        List<SolutonCandidate> neighbors = new ArrayList<>();
        for (BitVector next : gen) {
            satStats.setAssignment(next, false);
            neighbors.add(new SolutonCandidate(next, getFitness(satStats)));
        }
        return neighbors;
    }

    /**
     * Template method for selecting next solution from neighbors list.
     *
     * @param neighborhood
     *            list of neighbors (possible next solution)
     * @param rnd
     *            random generator
     * @return next solution
     */
    abstract protected SolutonCandidate pickNextSolution(final List<SolutonCandidate> neighborhood, final Random rnd);

    /**
     * Fitness function for solution given as {@link SATFormulaStats}.
     *
     * @param satStats
     *            {@link SATFormulaStats}
     * @return value of fitness
     */
    abstract protected double getFitness(final SATFormulaStats satStats);

    /**
     * Template method that checks if exit condition is met.
     *
     * @param oldSolution
     *            solution from previous iteration
     * @param newSolution
     *            solution in current iteration
     * @return true if exit condition is satisfied
     */
    abstract protected boolean exitCondition(SolutonCandidate oldSolution, SolutonCandidate newSolution);
}
