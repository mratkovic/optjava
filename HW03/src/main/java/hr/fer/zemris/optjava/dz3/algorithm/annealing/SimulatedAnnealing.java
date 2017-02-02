package hr.fer.zemris.optjava.dz3.algorithm.annealing;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.algorithm.AbstractIterativeAlgorithm;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.solution.IDecoder;
import hr.fer.zemris.optjava.dz3.solution.INeighborhood;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

public class SimulatedAnnealing<T extends SingleObjectiveSolution> extends AbstractIterativeAlgorithm<T> {

    private final Random rand;
    private final ITempSchedule tempSchedule;

    public SimulatedAnnealing(final IDecoder<T> decoder, final INeighborhood<T> neighborhood, final T startWith,
            final IFunction function, final boolean minimize, final Random rand, final ITempSchedule tempSchedule) {
        super(decoder, neighborhood, startWith, function, minimize);
        this.rand = rand;
        this.tempSchedule = tempSchedule;
    }

    @Override
    public void run() {
        T currentSolution = startWith;
        currentSolution.fitness = getFitness(currentSolution);
        double temperature = tempSchedule.getNextTemperature();

        for (int i = 0; i < tempSchedule.getOuterLoopCounter(); i++) {
            for (int j = 0; j < tempSchedule.getInnerLoopCounter(); j++) {

                T next = neighborhood.randomNeighbor(currentSolution);
                next.fitness = getFitness(next);

                double delta = currentSolution.fitness - next.fitness;

                if (delta <= 0 || rand.nextDouble() <= Math.exp(-delta / temperature)) {
                    currentSolution = next;
                }
            }
            temperature = tempSchedule.getNextTemperature();
            printStatus(currentSolution, i);
        }

        printSolution(currentSolution);
    }

}
