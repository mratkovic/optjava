package hr.fer.zemris.optjava.dz11;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.optjava.ga.cross.ICross;
import hr.fer.zemris.optjava.ga.mutation.IMutation;
import hr.fer.zemris.optjava.ga.selection.ISelection;
import hr.fer.zemris.optjava.ga.solution.RectSolution;
import hr.fer.zemris.optjava.rng.EVOThread;

public class GA1<T extends RectSolution> extends AbstractGA<T> {
    ConcurrentLinkedQueue<T> evalQueue;
    T poisonPill;

    public GA1(final ISelection<T> selection, final IMutation<T> mutation, final ICross<T> cross,
            final double fitnessLimit, final ConcurrentLinkedQueue<T> inputQueue,
            final ConcurrentLinkedQueue<T> outputQueue, final EVOThread[] threads, final GrayScaleImage im) {
        super(selection, mutation, cross, fitnessLimit, outputQueue, threads, im);
        evalQueue = inputQueue;

    }

    @Override
    void killWorkers() {
        for (EVOThread thread : threads) {
            evalQueue.add(poisonPill);
        }

    }

    @Override
    List<T> runSingleStep(final List<T> population) {
        List<T> nextGen = new ArrayList<>();
        for (int i = 0; i < eliteN; ++i) {
            nextGen.add(population.get(i));
        }
        for (int i = eliteN; i < population.size(); ++i) {
            T p1 = selection.selectFromPopulation(population);
            T p2 = selection.selectFromPopulation(population);
            T child = cross.crossParents(p1, p2);
            child = mutation.mutate(child);

            evalQueue.add(child);
        }

        fillFromProcessed(nextGen, population.size());
        nextGen.sort(descComparator);
        return nextGen;
    }

    @Override
    List<T> initialEvalPopulation(final List<T> population, final int populationSize) {
        for (T sol : population) {
            evalQueue.add(sol);
        }
        population.clear();
        fillFromProcessed(population, populationSize);
        population.sort(descComparator);
        return population;
    }

    @Override
    void initPoisonPill(final List<T> population) {
        poisonPill = (T) population.get(0).duplicate();
        poisonPill.fitness = Double.MAX_VALUE;
    }

}
