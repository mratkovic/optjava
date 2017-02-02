package hr.fer.zemris.optjava.dz4.ga;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.function.IFunction;
import hr.fer.zemris.optjava.ga.cross.ICross;
import hr.fer.zemris.optjava.ga.decoder.IDecoder;
import hr.fer.zemris.optjava.ga.mutation.IMutation;
import hr.fer.zemris.optjava.ga.selection.ISelection;
import hr.fer.zemris.optjava.ga.solution.SingleObjectiveSolution;

public class EliminationGA<T extends SingleObjectiveSolution> extends AbstractGA<T> {
    boolean alwaysKeepChild;
    ISelection<T> selectForElimination;

    public EliminationGA(final IFunction f, final IDecoder<T> decoder, final int populationSize, final double minValue,
            final int maxIteration, final ISelection<T> selection, final ISelection<T> selectForElimination,
            final IMutation<T> mutation, final ICross<T> cross, final Random rnd, final boolean minimize,
            final int printCnt, final boolean alwaysKeepChild) {
        super(f, decoder, populationSize, minValue, maxIteration, selection, mutation, cross, rnd, minimize, printCnt);
        this.alwaysKeepChild = alwaysKeepChild;
        this.selectForElimination = selectForElimination;
    }

    public EliminationGA(final IFunction f, final IFunction fitness, final IDecoder<T> decoder,
            final int populationSize, final double minValue, final int maxIteration, final ISelection<T> selection,
            final ISelection<T> selectForElimination, final IMutation<T> mutation, final ICross<T> cross,
            final Random rnd, final boolean minimize, final int printCnt, final boolean alwaysKeepChild) {
        super(f, fitness, decoder, populationSize, minValue, maxIteration, selection, mutation, cross, rnd, minimize,
                printCnt);
        this.alwaysKeepChild = alwaysKeepChild;
        this.selectForElimination = selectForElimination;
    }

    @Override
    protected List<T> runSingleIteration(final List<T> population) {
        T p1 = selection.selectFromPopulation(population, rnd);
        T p2 = selection.selectFromPopulation(population, rnd);

        List<T> children = cross.crossParents(p1, p2);
        T bestChild = mutation.mutate(findBest(children));

        T toReplace = selectForElimination.selectFromPopulation(population, rnd);
        if (bestChild.fitness > toReplace.fitness || alwaysKeepChild) {
            population.remove(toReplace);
            population.add(bestChild);
        }

        evaluatePopulation(population);
        return population;
    }

}
