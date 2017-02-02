package hr.fer.zemris.optjava.dz4.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.function.IFunction;
import hr.fer.zemris.optjava.ga.cross.ICross;
import hr.fer.zemris.optjava.ga.decoder.IDecoder;
import hr.fer.zemris.optjava.ga.mutation.IMutation;
import hr.fer.zemris.optjava.ga.selection.ISelection;
import hr.fer.zemris.optjava.ga.solution.SingleObjectiveSolution;

public class GenerationGA<T extends SingleObjectiveSolution> extends AbstractGA<T> {

    private final int keepEliteN;

    public GenerationGA(final IFunction f, final IDecoder<T> decoder, final int populationSize, final double minValue,
            final int maxIteration, final ISelection<T> selection, final IMutation<T> mutation, final ICross<T> cross,
            final Random rnd, final int keepEliteN, final boolean minimize, final int printCnt) {
        super(f, decoder, populationSize, minValue, maxIteration, selection, mutation, cross, rnd, minimize, printCnt);
        this.keepEliteN = keepEliteN;

    }

    GenerationGA(final IFunction f, final IFunction fitness, final IDecoder<T> decoder, final int populationSize,
            final double minValue, final int maxIteration, final ISelection<T> selection, final IMutation<T> mutation,
            final ICross<T> cross, final Random rnd, final int keepEliteN, final boolean minimize, final int printCnt) {
        super(f, fitness, decoder, populationSize, minValue, maxIteration, selection, mutation, cross, rnd, minimize,
                printCnt);
        this.keepEliteN = keepEliteN;
    }

    @Override
    protected List<T> runSingleIteration(List<T> population) {
        List<T> nextGen = new ArrayList<>();

        for (int i = 0; i < keepEliteN; ++i) {
            nextGen.add(population.get(i));
        }

        while (nextGen.size() < populationSize) {
            T p1 = selection.selectFromPopulation(population, rnd);
            T p2 = selection.selectFromPopulation(population, rnd);

            List<T> children = cross.crossParents(p1, p2);
            T bestChild = mutation.mutate(findBest(children));
            nextGen.add(bestChild);
        }
        population.clear();
        population = nextGen;
        evaluatePopulation(population);
        return population;
    }

}
