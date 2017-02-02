package hr.fer.zemris.optjava.dz6;

import java.io.IOException;

import hr.fer.zemris.optjava.dz6.aco.ACO;
import hr.fer.zemris.optjava.dz6.aco.Ant;
import hr.fer.zemris.optjava.dz6.tsp.Graph;

public class TSPSolver {

    private static final double alpha = 1.2;
    private static final double beta = 4.5;
    private static double a;
    private static double ro = 0.018;

    public static void main(final String[] args) throws IOException {

        String path = "tsp_problems/att48.tsp";
        int k = 10;
        int colonySize = 256;
        int maxIter = 3_000;

        if (args.length == 4) {
            k = Integer.parseInt(args[1]);
            colonySize = Integer.parseInt(args[2]);
            maxIter = Integer.parseInt(args[3]);
        } else {
            System.out.println("Expected 4 arguments (path, neighbor list size, ant colony size, max iter)");
            System.out.println("Using default arguments");
        }

        Graph graph = Graph.parseProblem(path);
        ACO aco = new ACO(graph, alpha, beta, k, ro, colonySize);
        Ant best = aco.run(maxIter);

        System.out.println("Distance = " + best.distance);
        best.printPath();
        // Path not always starting with node 1 (doesn't matter - still cycle)
    }
}
