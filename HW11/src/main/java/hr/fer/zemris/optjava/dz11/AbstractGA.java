package hr.fer.zemris.optjava.dz11;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.Evaluator;
import hr.fer.zemris.optjava.ga.cross.ICross;
import hr.fer.zemris.optjava.ga.mutation.IMutation;
import hr.fer.zemris.optjava.ga.selection.ISelection;
import hr.fer.zemris.optjava.ga.solution.RectSolution;
import hr.fer.zemris.optjava.rng.EVOThread;

public abstract class AbstractGA<T extends RectSolution> {

    ISelection<T> selection;
    IMutation<T> mutation;
    ICross<T> cross;
    double fitnessLimit;
    ConcurrentLinkedQueue<T> processedQueue;
    EVOThread[] threads;
    protected final int eliteN = 2;

    GrayScaleImage im;

    public AbstractGA(final ISelection<T> selection, final IMutation<T> mutation, final ICross<T> cross,
            final double fitnessLimit, final ConcurrentLinkedQueue<T> outputQueue, final EVOThread[] threads,
            final GrayScaleImage im) {
        super();
        this.selection = selection;
        this.mutation = mutation;
        this.cross = cross;
        this.fitnessLimit = fitnessLimit;
        this.processedQueue = outputQueue;
        this.threads = threads;
        this.im = im;
    }

    public void run(List<T> population, final int maxIter, final String txtDumpPath, final String imgOutPath)
            throws IOException {
        int iter = 0;
        int populationSize = population.size();
        initPoisonPill(population);

        // start workers
        for (EVOThread thread : threads) {
            thread.start();
        }
        population = initialEvalPopulation(population, populationSize);

        while (iter++ < maxIter) {
            population = runSingleStep(population);
            T best = population.get(0);
            if (iter % 100 == 0) {
                System.out.printf("%4d: fitness %.4f\n", iter, best.fitness);
            }
            if (iter % 1000 == 0) {
                dumpImage(String.format("aprox_%05d.png", iter), best);
            }

            if (population.get(0).fitness > fitnessLimit) {
                break;
            }
        }

        killWorkers();
        T best = population.get(0);
        System.out.println("\nGA completed, Fitness:" + best.fitness);
        dumpImage(imgOutPath, best);
        System.out.println("Saved output img to " + imgOutPath);
        dumpParams(txtDumpPath, best);
        System.out.println("Dumped params to " + txtDumpPath);

    }

    abstract void initPoisonPill(final List<T> population);

    abstract void killWorkers();

    abstract List<T> runSingleStep(final List<T> population);

    abstract List<T> initialEvalPopulation(final List<T> population, final int populationSize);

    protected final Comparator<T> descComparator = (p1, p2) -> -Double.compare(p1.fitness, p2.fitness);

    protected void fillFromProcessed(final List<T> population, final int populationSize) {
        // take processed
        while (population.size() < populationSize) {
            T sol = processedQueue.poll();
            if (sol != null) {
                population.add(sol);
            }
        }
    }

    private void dumpImage(final String path, final T best) throws IOException {
        GrayScaleImage bestImg = Evaluator.draw(best, im.width, im.height);
        bestImg.save(new File(path));

    }

    private void dumpParams(final String path, final T best) throws IOException {
        FileWriter fout = new FileWriter(path);
        for (int param : best.data) {
            fout.write(param + "\n");
        }
        fout.close();
    }

}
