package hr.fer.zemris.optjava.dz3.algorithm;

import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.solution.IDecoder;
import hr.fer.zemris.optjava.dz3.solution.INeighborhood;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

public class GreedyAlgorithm<T extends SingleObjectiveSolution> extends AbstractIterativeAlgorithm<T> {
    private final int maxIterations;

    public GreedyAlgorithm(final IDecoder<T> decoder, final INeighborhood<T> neighborhood, final T startWith,
            final IFunction function, final boolean minimize, final int maxIterations) {
        super(decoder, neighborhood, startWith, function, minimize);
        this.maxIterations = maxIterations;
    }

    @Override
    public void run() {
        T currentSolution = startWith;
        currentSolution.fitness = getFitness(currentSolution);

        int it = 0;
        while (it < maxIterations) {
            it += 1;
            T next = neighborhood.randomNeighbor(currentSolution);
            next.fitness = getFitness(next);

            if (next.compareTo(currentSolution) > 0) {
                currentSolution = next;
            }
            if (it % STEP == 0) {
                printStatus(currentSolution, it);
            }
        }
        printSolution(currentSolution);
    }

}
