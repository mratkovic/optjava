package hr.fer.zemris.optjava.dz11;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.paralel.GAReporoduceJob;
import hr.fer.zemris.optjava.ga.cross.ICross;
import hr.fer.zemris.optjava.ga.mutation.IMutation;
import hr.fer.zemris.optjava.ga.selection.ISelection;
import hr.fer.zemris.optjava.ga.solution.RectSolution;
import hr.fer.zemris.optjava.rng.EVOThread;

public class Pokretac2 extends AbstractRunner {

    public static void main(final String[] args) throws IOException {
        AbstractRunner runner = new Pokretac2();
        runner.run(args);

    }

    @Override
    protected void initAndStartGA(final String imgPath, final int nRects, final int populationSize, final int maxIter,
            final double minFittness, final String txtDumpPath, final String imgOutputPath, final GrayScaleImage img,
            final ISelection<RectSolution> selection, final IMutation<RectSolution> mutation,
            final ICross<RectSolution> cross, final int cores, final ConcurrentLinkedQueue<RectSolution> processedQueue)
            throws IOException {

        ConcurrentLinkedQueue<Pair<Integer, List<RectSolution>>> evalQueue = new ConcurrentLinkedQueue<>();
        EVOThread[] threads = new EVOThread[cores];
        GrayScaleImage[] imgs = new GrayScaleImage[cores];

        for (int i = 0; i < cores; ++i) {
            imgs[i] = GrayScaleImage.load(new File(imgPath));
            Runnable job = new GAReporoduceJob<>(i, evalQueue, processedQueue, selection, mutation, cross);
            threads[i] = new EVOThread(job, imgs[i]);
        }

        List<RectSolution> population = initPopulation(populationSize, nRects, img);
        AbstractGA<RectSolution> ga = new GA2<>(selection, mutation, cross, minFittness, evalQueue, processedQueue,
                threads, img);
        ga.run(population, maxIter, txtDumpPath, imgOutputPath);

    }

}
