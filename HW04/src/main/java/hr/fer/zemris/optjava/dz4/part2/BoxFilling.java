package hr.fer.zemris.optjava.dz4.part2;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz4.function.IFunction;
import hr.fer.zemris.optjava.dz4.ga.EliminationGA;
import hr.fer.zemris.optjava.ga.cross.ICross;
import hr.fer.zemris.optjava.ga.mutation.IMutation;
import hr.fer.zemris.optjava.ga.selection.ISelection;
import hr.fer.zemris.optjava.ga.selection.Tournament;

public class BoxFilling {
    // for all MC files
    private static final int MAX_HEIGHT = 20;

    public static void main(final String[] args) throws IOException {

        /** Default parameters */
        Random rnd = new Random();
        int populationSize = 50;
        int n = 10;
        int m = 3;
        boolean alwaysKeepChild = true;
        int maxIter = 200_000;
        double minError = 5;
        String filepath = "./kutije/problem-20-30-1.dat";

        // args = new String[] { "./kutije/problem-20-30-1.dat", "50", "10",
        // "3",
        // "true", "200000", "5" };

        if (args.length != 7) {
            System.err.println("Expected 5 arguments, continuing using default arguments");

        } else {
            filepath = args[0];
            populationSize = Integer.parseInt(args[1]);
            n = Integer.parseInt(args[2]);
            m = Integer.parseInt(args[3]);
            alwaysKeepChild = Boolean.parseBoolean(args[4]);
            maxIter = Integer.parseInt(args[5]);
            minError = Integer.parseInt(args[6]);
        }

        BoxSolutionDecoder decoder = new BoxSolutionDecoder(MAX_HEIGHT, filepath);
        int components = decoder.stickHeights.length;
        BoxFillingFunction f = new BoxFillingFunction();
        IFunction cost = vs -> {
            return vs.length;// width of box
        };

        ISelection<BoxSolution> selection = new Tournament<>(n);
        ISelection<BoxSolution> selectionForElimination = new Tournament<>(m, false);
        ICross<BoxSolution> cross = new OX2Cross(rnd);
        IMutation<BoxSolution> mutation = new SwapStickMutation(rnd);

        List<BoxSolution> population = generateInitialPopulation(rnd, components, populationSize);
        EliminationGA<BoxSolution> ga = new EliminationGA<>(cost, f, decoder, populationSize, minError, maxIter,
                selection, selectionForElimination, mutation, cross, rnd, true, -10000, alwaysKeepChild);

        ga.run(population);
    }

    private static List<BoxSolution> generateInitialPopulation(final Random rnd, final int n,
            final int populationSize) {

        List<BoxSolution> population = new LinkedList<>();

        for (int i = 0; i < populationSize; i++) {
            BoxSolution s = new BoxSolution(n);
            s.randomize(rnd);
            population.add(s);

        }

        return population;
    }

}
