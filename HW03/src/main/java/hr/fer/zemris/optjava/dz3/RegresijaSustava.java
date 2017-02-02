package hr.fer.zemris.optjava.dz3;

import java.util.Random;
import java.util.Scanner;

import hr.fer.zemris.optjava.dz3.algorithm.annealing.GeometricTempSchedule;
import hr.fer.zemris.optjava.dz3.algorithm.annealing.ITempSchedule;
import hr.fer.zemris.optjava.dz3.algorithm.annealing.SimulatedAnnealing;
import hr.fer.zemris.optjava.dz3.function.CoefficientsFunction;
import hr.fer.zemris.optjava.dz3.solution.IDecoder;
import hr.fer.zemris.optjava.dz3.solution.INeighborhood;
import hr.fer.zemris.optjava.dz3.solution.bit.BitvectorSingleBitNeighborhood;
import hr.fer.zemris.optjava.dz3.solution.bit.BitvectorSolution;
import hr.fer.zemris.optjava.dz3.solution.bit.GrayCodeDecoder;
import hr.fer.zemris.optjava.dz3.solution.doublearray.DoubleArraySolution;
import hr.fer.zemris.optjava.dz3.solution.doublearray.DoubleArrayUnifNeighborhood;
import hr.fer.zemris.optjava.dz3.solution.doublearray.PassThroughDecoder;

public class RegresijaSustava {
    /** Initial solution range. */
    public static final double min = -10;
    public static final double max = 10;

    /** Neighborhood param */
    public static final double delta = 5;

    /** Minimize or maximize function. */
    public static final boolean minimization_problem = true;

    /**
     * Simulated annealing params
     */
    public static final double alpha = 0.975;
    public static final double beginingTemp = 1000;
    public static final int innerLimit = 10000;
    public static final int outerLimit = 1000;

    public static void main(final String[] args) {

        if (args.length != 0 && args.length != 2) {
            throw new IllegalArgumentException("Invalid call; Expected <file_path> <decimal|binary:n>");
        }

        String representation = null, filePath = null;

        if (args.length == 0) {
            // ask for arguments
            Scanner sc = new Scanner(System.in);

            System.out.println("File path:");
            filePath = sc.nextLine().trim();

            System.out.println("Representation [decimal | binary:n>]:");
            representation = sc.nextLine().trim();

        } else if (args.length == 2) {
            filePath = args[0];
            representation = args[1];
        }

        CoefficientsFunction f = new CoefficientsFunction(filePath);

        if (representation.equals("decimal")) {
            decimalRepresentationSolution(f);

        } else if (representation.startsWith("binary")) {
            int bitsPerVar = Integer.parseInt(representation.split(":")[1]);
            binaryRepresentationSolution(f, bitsPerVar);
        } else {
            throw new IllegalArgumentException("Invalid representation: " + representation);
        }
    }

    private static void binaryRepresentationSolution(final CoefficientsFunction f, final int bitsPerVar) {
        Random rand = new Random();
        ITempSchedule tp = new GeometricTempSchedule(alpha, beginingTemp, innerLimit, outerLimit);

        IDecoder<BitvectorSolution> decoder = new GrayCodeDecoder(min, max, bitsPerVar, f.numberOfVariables());
        INeighborhood<BitvectorSolution> neighborhood = new BitvectorSingleBitNeighborhood();

        BitvectorSolution start = new BitvectorSolution(f.numberOfVariables() * bitsPerVar);
        start.randomize(rand);

        SimulatedAnnealing<BitvectorSolution> solver = new SimulatedAnnealing<>(decoder, neighborhood, start, f,
                minimization_problem, rand, tp);
        solver.run();
    }

    private static void decimalRepresentationSolution(final CoefficientsFunction f) {
        Random rand = new Random();
        ITempSchedule tp = new GeometricTempSchedule(alpha, beginingTemp, innerLimit, outerLimit);

        IDecoder<DoubleArraySolution> decoder = new PassThroughDecoder();
        INeighborhood<DoubleArraySolution> neighborhood = new DoubleArrayUnifNeighborhood(f.numberOfVariables(), delta);

        DoubleArraySolution start = new DoubleArraySolution(f.numberOfVariables());
        start.randomize(rand, min, max);
        SimulatedAnnealing<DoubleArraySolution> solver = new SimulatedAnnealing<>(decoder, neighborhood, start, f,
                minimization_problem, rand, tp);
        solver.run();
    }
}