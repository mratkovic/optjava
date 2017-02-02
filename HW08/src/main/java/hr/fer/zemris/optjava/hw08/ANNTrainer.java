package hr.fer.zemris.optjava.hw08;

import java.io.IOException;
import java.util.Random;

import hr.fer.zemris.data.IDataset;
import hr.fer.zemris.data.TimeSequenceDataset;
import hr.fer.zemris.optimizations.ANNFunction;
import hr.fer.zemris.optimizations.IFunction;
import hr.fer.zemris.optimizations.ann.ElmanNN;
import hr.fer.zemris.optimizations.ann.FFANN;
import hr.fer.zemris.optimizations.ann.FunctionFactory;
import hr.fer.zemris.optimizations.ann.IANN;
import hr.fer.zemris.optimizations.de.DifferentialEvolution;
import hr.fer.zemris.optimizations.de.IDiferentialEvolutionStrategy;
import hr.fer.zemris.optimizations.de.TargetToBestStrategy;

public class ANNTrainer {
    public static void main(final String[] args) throws IOException {
        // default args
        String path = "08-Laser-generated-data.txt";
        String net = "elman-1x5x1";
        // String net = "tdnn-5x10x1";
        int popSize = 100;
        double merr = 0.01;
        int maxIter = 1_0000;

        // DE params
        double F = 0.15;
        int nLinearCombinations = 1;
        double crossChance = 0.2;
        double wMin = -1;
        double wMax = 1;
        int nSamples = 100;

        // Parse args
        if (args.length == 5) {
            path = args[0];
            net = args[1];
            popSize = Integer.parseInt(args[2]);
            merr = Double.parseDouble(args[3]);
            maxIter = Integer.parseInt(args[4]);

        } else {
            System.out.println("Expected 5 arguments (path, algo, popsize, mean err, max iter)");
            System.out.println("Using default arguments");
        }

        Random rnd = new Random();
        IDiferentialEvolutionStrategy strategy = new TargetToBestStrategy(F, rnd, nLinearCombinations);

        if (net.startsWith("tdnn-")) {
            String arh = net.split("-")[1].trim();

            IDataset dataset = TimeSequenceDataset.load(path, 5, nSamples);
            IANN ffann = new FFANN(arh, FunctionFactory.TANH, dataset);
            IFunction func = new ANNFunction(ffann);
            DifferentialEvolution de = new DifferentialEvolution(strategy, func, popSize, crossChance, wMin, wMax, true,
                    rnd);
            double[] ws = de.run(maxIter, merr);
            System.out.printf("\nMSE: %4.6f\n", func.valueAt(ws));

        } else {
            String arh = net.split("-")[1].trim();

            IDataset dataset = TimeSequenceDataset.load(path, 1, nSamples);
            IANN ffann = new ElmanNN(arh, FunctionFactory.TANH, dataset);
            IFunction func = new ANNFunction(ffann);
            DifferentialEvolution de = new DifferentialEvolution(strategy, func, popSize, crossChance, wMin, wMax, true,
                    rnd);
            double[] ws = de.run(maxIter, merr);
            System.out.printf("\nMSE: %4.6f\n", func.valueAt(ws));
        }
    }
}
