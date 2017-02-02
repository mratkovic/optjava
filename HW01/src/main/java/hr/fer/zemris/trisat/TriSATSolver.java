package hr.fer.zemris.trisat;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import hr.fer.zemris.trisat.algorithm.Algorithm1;
import hr.fer.zemris.trisat.algorithm.Algorithm2;
import hr.fer.zemris.trisat.algorithm.Algorithm3;
import hr.fer.zemris.trisat.algorithm.IAlgorithm;

/**
 * Class that models solver for TRISat problem.
 *
 * @author marko
 *
 */
public class TriSATSolver {
    /** Hash map with implementation of different solving algorithms. */
    private static final Map<String, IAlgorithm> ALGORITHMS;

    static {
        ALGORITHMS = new HashMap<>();
        ALGORITHMS.put("1", new Algorithm1());
        ALGORITHMS.put("2", new Algorithm2());
        ALGORITHMS.put("3", new Algorithm3());
    };

    /**
     * Program expects two arguments:
     * <ul>
     * <li><algo_id> index of algorithm [1,2,3]</li>
     * <li><file_path> path to cnf file with problem definition.</li>
     * </ul>
     *
     * @param args
     */
    public static void main(final String[] args) {
        String algoId, filepath;

        if (args.length == 0) {
            // ask if no parameters are passed
            // easier for testing
            Scanner sc = new Scanner(System.in);
            System.out.print("Algoritam id:");
            algoId = sc.nextLine().trim();

            System.out.print("FilePath:");
            filepath = sc.nextLine().trim();

        } else if (args.length == 2) {
            algoId = args[0];
            filepath = args[1];
        } else {
            throw new IllegalArgumentException("Expected arguments <algorithm_id> <conf_file_path>");
        }

        if (!ALGORITHMS.containsKey(algoId)) {
            throw new IllegalArgumentException("No such algorithm: '" + algoId + "'");
        }

        SATFormula sat = Parser.parseFile(filepath);
        IAlgorithm algorithm = ALGORITHMS.get(algoId);

        algorithm.solve(sat);
    }
}
