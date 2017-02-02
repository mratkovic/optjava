package hr.fer.zemris.optjava.hw07;

import java.io.IOException;
import java.util.Random;

import hr.fer.zemris.data.IDataset;
import hr.fer.zemris.data.IrisDataset;
import hr.fer.zemris.optimizations.IFunction;
import hr.fer.zemris.optimizations.ann.FFANN;
import hr.fer.zemris.optimizations.ann.FunctionFactory;
import hr.fer.zemris.optimizations.ann.ITransferFunction;
import hr.fer.zemris.optimizations.clonalg.ClonAlg;
import hr.fer.zemris.optimizations.pso.FFANNFunction;
import hr.fer.zemris.optimizations.pso.PSO;
import hr.fer.zemris.optimizations.pso.Swarm;

public class ANNTrainer {
    public static void main(final String[] args) throws IOException {
        String path = "07-iris-formatirano.data";
        String alg = "pso-a";
        int popSize = 100;
        double merr = 1e-3;
        int maxIter = 5_000;

        if (args.length == 5) {
            path = args[0];
            alg = args[1];
            popSize = Integer.parseInt(args[2]);
            merr = Double.parseDouble(args[3]);
            maxIter = Integer.parseInt(args[4]);

        } else {
            System.out.println("Expected 5 arguments (path, algo, popsize, mean err, max iter)");
            System.out.println("Using default arguments");
        }

        IDataset dataset = IrisDataset.load(path);
        ITransferFunction[] activations = new ITransferFunction[] { FunctionFactory.SIGMOID2, FunctionFactory.SIGMOID2,
                FunctionFactory.SIGMOID2 };
        FFANN ffann = new FFANN(new int[] { 4, 5, 3, 3 }, activations, dataset);
        IFunction func = new FFANNFunction(ffann);

        double[] weights = null;
        if (alg.startsWith("pso")) {
            weights = PSO(func, popSize, alg, maxIter, merr);
        } else {
            weights = clonAlg(func, popSize, maxIter, merr);
        }
        ffann.stats(weights);
    }

    private static double[] clonAlg(final IFunction func, final int popSize, final int maxIter, final double merr) {
        double wMin = -1;
        double wMax = 1;

        double ro = 4;
        int mutateN = 3;

        double beta = 2;
        int nNew = (int) (0.1 * popSize);
        Random rnd = new Random();

        ClonAlg alg = new ClonAlg(func, popSize, beta, nNew, ro, mutateN, wMin, wMax, true, rnd);
        return alg.run(maxIter, merr);
    }

    private static double[] PSO(final IFunction f, final int populationSize, final String algo, final int maxIter,
            final double merr) {
        // params
        double wMin = 0;
        double wMax = 1;
        double c1 = 2, c2 = 2;

        double xMin = -1;
        double xMax = 1;
        double vMin = -1;
        double vMax = 1;

        Random rnd = new Random();

        Swarm swarm = new Swarm(populationSize, f.nVariables());
        swarm.initializeParticles(rnd, xMin, xMax, vMin, vMax);
        swarm.initializeNeighborhood(algo);

        PSO pso = new PSO(f, swarm, true, rnd, vMin, vMax, c1, c2, wMin, wMax);
        return pso.run(maxIter, merr);

    }
}
