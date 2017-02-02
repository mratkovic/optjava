package hr.fer.zemris.generic.ga.paralel;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.generic.ga.IEvaluatorProvider;
import hr.fer.zemris.optjava.dz11.Pair;
import hr.fer.zemris.optjava.ga.cross.ICross;
import hr.fer.zemris.optjava.ga.mutation.IMutation;
import hr.fer.zemris.optjava.ga.selection.ISelection;
import hr.fer.zemris.optjava.ga.solution.GASolution;

public class GAReporoduceJob<T extends GASolution<int[]>> implements Runnable {
    int id;
    public ConcurrentLinkedQueue<Pair<Integer, List<T>>> inputQueue;
    public ConcurrentLinkedQueue<T> outputQueue;
    ISelection<T> selection;
    IMutation<T> mutation;
    ICross<T> cross;

    public GAReporoduceJob(final int id, final ConcurrentLinkedQueue<Pair<Integer, List<T>>> inputQueue,
            final ConcurrentLinkedQueue<T> outputQueue, final ISelection<T> selection, final IMutation<T> mutation,
            final ICross<T> cross) {
        super();
        this.id = id;
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
        this.selection = selection;
        this.mutation = mutation;
        this.cross = cross;
    }

    @Override
    public void run() {
        Evaluator evaluator = ((IEvaluatorProvider) Thread.currentThread()).getEvaluator();
        Pair<Integer, List<T>> in = null;

        while (true) {
            in = inputQueue.poll();
            if (in == null) {
                Thread.yield();
            } else if (isPill(in)) {
                System.out.println("Worker shutting down: " + id);
                break;

            } else {
                int n = in.first;
                List<T> population = in.second;
                for (int i = 0; i < n; ++i) {
                    T p1 = selection.selectFromPopulation(population);
                    T p2 = selection.selectFromPopulation(population);
                    T child = cross.crossParents(p1, p2);
                    child = mutation.mutate(child);

                    evaluator.evaluate(child);
                    outputQueue.add(child);
                }
            }

        }
    }

    private boolean isPill(final Pair<Integer, List<T>> in) {
        return in.first.intValue() == -1;
    }

}
