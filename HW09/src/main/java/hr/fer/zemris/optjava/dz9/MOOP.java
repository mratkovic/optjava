package hr.fer.zemris.optjava.dz9;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.moop.cross.BLXCross;
import hr.fer.zemris.optjava.moop.cross.ICross;
import hr.fer.zemris.optjava.moop.distance.DecisionSpaceDistance;
import hr.fer.zemris.optjava.moop.distance.IDistance;
import hr.fer.zemris.optjava.moop.distance.ObjectiveSpaceDistance;
import hr.fer.zemris.optjava.moop.ga.NSGA;
import hr.fer.zemris.optjava.moop.mutation.IMutation;
import hr.fer.zemris.optjava.moop.mutation.NormMutation;
import hr.fer.zemris.optjava.moop.problems.MOOPProblem;
import hr.fer.zemris.optjava.moop.problems.Problem1;
import hr.fer.zemris.optjava.moop.problems.Problem2;
import hr.fer.zemris.optjava.moop.selection.ISelection;
import hr.fer.zemris.optjava.moop.selection.RouletteWheelSelection;
import hr.fer.zemris.optjava.moop.solution.DoubleArraySolution;

public class MOOP {
    private static final double BLX_ALPHA = 0.8;
    private static final MOOPProblem[] PROBLEMS = { new Problem1(), new Problem2() };
    private static final HashMap<String, IDistance<DoubleArraySolution>> DISTANCE;

    static {
        DISTANCE = new HashMap<>();
        DISTANCE.put("decision-space", new DecisionSpaceDistance());
        DISTANCE.put("objective-space", new ObjectiveSpaceDistance());
    }

    public static void main(String[] args) throws IOException {
        args = new String[] { "2", "200", "objective-space", "5000" };

        /** Default parameters */
        Random rnd = new Random();
        int populationSize = 200;
        int maxIter = 5000;
        double sigmaShare = 0.1;
        double alpha = 2;
        double sigma = 0.4; // 0.2

        MOOPProblem problem = new Problem2();

        ISelection<DoubleArraySolution> selection = new RouletteWheelSelection<>();
        ICross<DoubleArraySolution> cross = new BLXCross(BLX_ALPHA, rnd);
        IMutation<DoubleArraySolution> mutation = new NormMutation(sigma, rnd);
        IDistance<DoubleArraySolution> distance = new DecisionSpaceDistance();

        if (args.length != 4) {
            System.err.println("Expected 4 arguments, continuing using default arguments");

        } else {
            int problemID = Integer.parseInt(args[0]);
            problem = PROBLEMS[problemID - 1];
            populationSize = Integer.parseInt(args[1]);

            String distanceType = args[2].trim();
            distance = DISTANCE.get(distanceType);
            maxIter = Integer.parseInt(args[3]);
        }

        List<DoubleArraySolution> population = generateInitialPopulation(rnd, problem, populationSize);
        NSGA<DoubleArraySolution> nsga = new NSGA<>(problem, selection, mutation, cross, distance, rnd, sigmaShare,
                alpha);
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