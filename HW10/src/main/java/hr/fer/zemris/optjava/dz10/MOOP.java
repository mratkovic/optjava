package hr.fer.zemris.optjava.dz10;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.moop.cross.BLXCross;
import hr.fer.zemris.optjava.moop.cross.ICross;
import hr.fer.zemris.optjava.moop.ga.NSGA2;
import hr.fer.zemris.optjava.moop.mutation.IMutation;
import hr.fer.zemris.optjava.moop.mutation.NormMutation;
import hr.fer.zemris.optjava.moop.problems.MOOPProblem;
import hr.fer.zemris.optjava.moop.problems.Problem1;
import hr.fer.zemris.optjava.moop.problems.Problem2;
import hr.fer.zemris.optjava.moop.selection.CrowdedTournament;
import hr.fer.zemris.optjava.moop.selection.ISelection;
import hr.fer.zemris.optjava.moop.solution.DoubleArraySolution;

public class MOOP {
    private static final double BLX_ALPHA = 0.5;
    private static final MOOPProblem[] PROBLEMS = { new Problem1(), new Problem2() };

    public static void main(final String[] args) throws IOException {
        // function, pop_size, max_iter
        // args = new String[] { "2", "50", "100" };

        /** Default parameters */
        Random rnd = new Random();
        int populationSize = 50;
        int maxIter = 500;
        double sigma = 0.05;
        int tournamentSize = 3;

        MOOPProblem problem = new Problem2();

        ISelection<DoubleArraySolution> selection = new CrowdedTournament<>(tournamentSize);
        ICross<DoubleArraySolution> cross = new BLXCross(BLX_ALPHA, rnd);
        IMutation<DoubleArraySolution> mutation = new NormMutation(sigma, rnd);

        if (args.length != 3) {
            System.err.println("Expected 3 arguments, continuing using default arguments");

        } else {
            int problemID = Integer.parseInt(args[0]);
            problem = PROBLEMS[problemID - 1];
            populationSize = Integer.parseInt(args[1]);
            maxIter = Integer.parseInt(args[2]);
        }

        List<DoubleArraySolution> population = generateInitialPopulation(rnd, problem, populationSize);
        NSGA2<DoubleArraySolution> nsga = new NSGA2<>(problem, selection, mutation, cross, rnd);
        nsga.run(population, maxIter, true);
    }

    private static List<DoubleArraySolution> generateInitialPopulation(final Random rnd, final MOOPProblem f,
            final int populationSize) {

        List<DoubleArraySolution> population = new LinkedList<>();
        for (int i = 0; i < populationSize; i++) {
            DoubleArraySolution s = new DoubleArraySolution(f.getNumberOfObjectives(), f.numberOfVariables());
            s.randomize(rnd, f.domainLowerBound(), f.domainUpperBound());
            population.add(s);
        }
        return population;
    }
}