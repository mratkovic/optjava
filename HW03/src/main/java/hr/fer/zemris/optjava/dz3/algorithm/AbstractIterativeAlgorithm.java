package hr.fer.zemris.optjava.dz3.algorithm;

import java.util.Arrays;

import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.solution.IDecoder;
import hr.fer.zemris.optjava.dz3.solution.INeighborhood;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

public abstract class AbstractIterativeAlgorithm<T extends SingleObjectiveSolution> implements IOptAlgorithm<T> {
    protected static final int STEP = 100;
    protected final IDecoder<T> decoder;
    protected final INeighborhood<T> neighborhood;
    protected final T startWith;
    protected final IFunction fitnessFunction;
    protected final IFunction function;

    public AbstractIterativeAlgorithm(final IDecoder<T> decoder, final INeighborhood<T> neighborhood, final T startWith,
            final IFunction function, final boolean minimize) {
        this.decoder = decoder;
        this.neighborhood = neighborhood;
        this.startWith = startWith;
        this.function = function;
        this.fitnessFunction = values -> {
            int a = minimize ? -1 : 1;
            return a * function.valueAt(values);
        };
    }

    protected double getFitness(final T solution) {
        return fitnessFunction.valueAt(decoder.decode(solution));
    }

    protected void printStatus(final T currentSolution, final int it) {
        double[] values = decoder.decode(currentSolution);
        System.out.println(it + "\tf(x) = " + function.valueAt(values));
    }

    protected void printSolution(final T currentSolution) {
        System.out.println("Completed...");
        System.out.println("Solution: " + Arrays.toString(decoder.decode(currentSolution)));
        System.out.println("f(x)=" + function.valueAt(decoder.decode(currentSolution)));
    }

}
