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

public class GA2<T extends RectSolution> extends AbstractGA<T> {
    ConcurrentLinkedQueue<Pair<Integer, List<T>>> evalQueue;
    Pair<Integer, List<T>> poisonPill;
    int nWorkers;

    public GA2(final ISelection<T> selection, final IMutation<T> mutation, final ICross<T> cross,
            final double fitnessLimit, final ConcurrentLinkedQueue<Pair<Integer, List<T>>> inputQueue,
            final ConcurrentLinkedQueue<T> outputQueue, final EVOThread[] threads, final GrayScaleImage im) {
        super(selection, mutation, cross, fitnessLimit, outputQueue, threads, im);
        evalQueue = inputQueue;
        nWorkers = threads.length;

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
        int nTasks = population.size() - eliteN;

        int chunkSize = nTasks / nWorkers;
        int extra = nTasks % nWorkers;
        int total = 0;
        for (int i = 0; i < nWorkers; ++i) {
            int needToProcess = chunkSize;
            if (i < extra) {
                needToProcess++;
            }
            total += needToProcess;
            Pair<Integer, List<T>> task = new Pair<>(needToProcess, population);
            evalQueue.add(task);
        }

        if (total != nTasks) {
            throw new RuntimeException("Invalid number of tasks");
        }

        fillFromProcessed(nextGen, population.size());
        nextGen.sort(descComparator);
        return nextGen;
    }

    @Override
    void initPoisonPill(final List<T> population) {
        poisonPill = new Pair<>(-1, null);

    }

    @Override
    List<T> initialEvalPopulation(final List<T> population, final int populationSize) {
        List<T> nextGen = new ArrayList<>();
        nextGen.addAll(runSingleStep(population));
        population.clear();
        nextGen.sort(descComparator);
        return nextGen;
    }

}
