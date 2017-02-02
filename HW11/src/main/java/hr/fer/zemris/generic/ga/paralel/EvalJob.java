package hr.fer.zemris.generic.ga.paralel;

import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.generic.ga.IEvaluatorProvider;
import hr.fer.zemris.optjava.ga.solution.GASolution;

public class EvalJob<T extends GASolution<int[]>> implements Runnable {
    public int id;
    public ConcurrentLinkedQueue<T> evalQueue;
    public ConcurrentLinkedQueue<T> processedQueue;

    public EvalJob(final int id, final ConcurrentLinkedQueue<T> evalQueue,
            final ConcurrentLinkedQueue<T> processedQueue) {
        super();
        this.id = id;
        this.evalQueue = evalQueue;
        this.processedQueue = processedQueue;
    }

    @Override
    public void run() {
        Evaluator evaluator = ((IEvaluatorProvider) Thread.currentThread()).getEvaluator();
        T task = null;

        while (true) {
            task = evalQueue.poll();
            if (task == null) {
                Thread.yield();
            } else if (isPill(task)) {
                System.out.println("Worker shutting down: " + id);
                break;

            } else {
                evaluator.evaluate(task);
                processedQueue.add(task);
            }

        }
    }

    private boolean isPill(final T task) {
        return task.fitness == Double.MAX_VALUE;
    }

}
