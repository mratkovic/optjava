package hr.fer.zemris.optjava.dz11;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.optjava.ga.cross.ICross;
import hr.fer.zemris.optjava.ga.cross.SinglePointCross;
import hr.fer.zemris.optjava.ga.mutation.GaussMutation;
import hr.fer.zemris.optjava.ga.mutation.IMutation;
import hr.fer.zemris.optjava.ga.selection.ISelection;
import hr.fer.zemris.optjava.ga.selection.Tournament;
import hr.fer.zemris.optjava.ga.solution.RectSolution;
import hr.fer.zemris.optjava.rng.EVOThread;

public abstract class AbstractRunner {

    public void run(final String[] args) {
        Runnable job = new Runnable() {
            @Override
            public void run() {
                try {
                    main(args);
                } catch (IOException e) {
                    System.err.println("Could not load");
                    e.printStackTrace();
                }
            }

        };
        EVOThread thread = new EVOThread(job);
        thread.start();
    };

    private void main(final String[] args) throws IOException {
        String imgPath = "./kuca.png";
        int nRects = 250;
        int populationSize = 15;
        int maxIter = 1000000;
        double minFittness = 0;
        String txtDumpPath = "./dump.txt";
        String imgOutputPath = "./out.png";

        if (args.length != 7) {
            System.err.println(
                    "Invalid number of arguments, expected: img_path n_rects pop_size max_gen fitness dump_txt_path out_img_path");
            System.err.println("Using default arguments");

        } else {
            imgPath = args[0];

            txtDumpPath = args[5];
            imgOutputPath = args[6];

            nRects = Integer.parseInt(args[1]);
            populationSize = Integer.parseInt(args[2]);
            maxIter = Integer.parseInt(args[3]);
            minFittness = Double.parseDouble(args[4]);
        }

        GrayScaleImage img = GrayScaleImage.load(new File(imgPath));
        int[] bounds = new int[] { 255, img.width, img.height, img.width, img.height };

        ISelection<RectSolution> selection = new Tournament<>(3);
        IMutation<RectSolution> mutation = new GaussMutation<>(bounds, 10, 0.05);
        ICross<RectSolution> cross = new SinglePointCross<>();

        int cores = Runtime.getRuntime().availableProcessors();
        ConcurrentLinkedQueue<RectSolution> processedQueue = new ConcurrentLinkedQueue<>();

        initAndStartGA(imgPath, nRects, populationSize, maxIter, minFittness, txtDumpPath, imgOutputPath, img,
                selection, mutation, cross, cores, processedQueue);
    }

    protected List<RectSolution> initPopulation(final int populationSize, final int nRects, final GrayScaleImage img) {
        List<RectSolution> pop = new ArrayList<>();
        for (int i = 0; i < populationSize; ++i) {
            RectSolution r = new RectSolution(nRects * 5 + 1);
            r.randomize(img.width, img.height);
            r.fitness = -Double.MAX_VALUE;
            pop.add(r);
        }

        return pop;
    }

    protected abstract void initAndStartGA(String imgPath, int nRects, int populationSize, int maxIter,
            double minFittness, String txtDumpPath, String imgOutputPath, GrayScaleImage img,
            ISelection<RectSolution> selection, IMutation<RectSolution> mutation, ICross<RectSolution> cross, int cores,
            ConcurrentLinkedQueue<RectSolution> processedQueue) throws IOException;

}
