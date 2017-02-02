package hr.fer.zemris.optjava.dz4.part1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.function.CoefficientsFunction;
import hr.fer.zemris.optjava.dz4.ga.GenerationGA;
import hr.fer.zemris.optjava.ga.cross.BLXCross;
import hr.fer.zemris.optjava.ga.cross.ICross;
import hr.fer.zemris.optjava.ga.decoder.IDecoder;
import hr.fer.zemris.optjava.ga.decoder.PassThroughDecoder;
import hr.fer.zemris.optjava.ga.mutation.IMutation;
import hr.fer.zemris.optjava.ga.mutation.NormMutation;
import hr.fer.zemris.optjava.ga.selection.ISelection;
import hr.fer.zemris.optjava.ga.selection.RouletteWheelSelection;
import hr.fer.zemris.optjava.ga.selection.Tournament;
import hr.fer.zemris.optjava.ga.solution.DoubleArraySolution;

public class GeneticAlgorithm {
    private static final double MAX_VAL = 5;
    private static final double MIN_VAL = -5;
    private static final double BLX_ALPHA = 0.8;
    // print every iter
    private static final int PRINT_CNT = 100;

    public static void main(final String[] args) {

        /** Default parameters */
        Random rnd = new Random();
        int populationSize = 100;
        double minError = 0.05;
        int maxIter = 100000;
        double sigma = 0.05;

        CoefficientsFunction f = new CoefficientsFunction("02-zad-prijenosna.txt");
        IDecoder<DoubleArraySolution> decoder = new PassThroughDecoder();

        ISelection<DoubleArraySolution> selection = new Tournament<>(5);
        ICross<DoubleArraySolution> cross = new BLXCross(rnd, BLX_ALPHA);
        IMutation<DoubleArraySolution> mutation = new NormMutation(rnd, sigma);

        // params used for testing
        // args = new String[] { "100", "0.05", "100000", "rouletteWheel", "0.1"
        // };
        // args = new String[] { "100", "0.05", "100000", "tournament:5",
        // "0.015" };

        if (args.length != 5) {
            System.err.println("Expected 5 arguments, continuing using default arguments");

        } else {
            populationSize = Integer.parseInt(args[0]);
            minError = Double.parseDouble(args[1]);
            maxIter = Integer.parseInt(args[2]);
            selection = parseSelection(args[3]);
            sigma = Double.parseDouble(args[4]);
        }

        List<DoubleArraySolution> population = generateInitialPopulation(rnd, f, populationSize);

        GenerationGA<DoubleArraySolution> ga = new GenerationGA<>(f, decoder, populationSize, minError, maxIter,
                selection, mutation, cross, rnd, 2, true, PRINT_CNT);

        ga.run(population);
    }

    private static List<DoubleArraySolution> generateInitialPopulation(final Random rnd, final CoefficientsFunction f,
            final int populationSize) {

        List<DoubleArraySolution> population = new LinkedList<>();
        for (int i = 0; i < populationSize; i++) {
            DoubleArraySolution s = new DoubleArraySolution(f.numberOfVariables());
            s.randomize(rnd, MIN_VAL, MAX_VAL);
            population.add(s);
        }
        return population;
    }

    private static ISelection<DoubleArraySolution> parseSelection(final String strSelection) {
        if (strSelection.equals("rouletteWheel")) {
            return new RouletteWheelSelection<>();

        } else if (strSelection.startsWith("tournament:")) {
            String size = strSelection.split(":")[1];
            return new Tournament<>(Integer.parseInt(size));

        } else {
            throw new IllegalArgumentException("Invalid selection type string" + strSelection);
        }
    }
}
