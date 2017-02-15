package hr.fer.zemris.optjava.dz13;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.optjava.dz13.ant.AntMap;
import hr.fer.zemris.optjava.dz13.ant.ga.AntCrossover;
import hr.fer.zemris.optjava.dz13.ant.ga.AntMutation;
import hr.fer.zemris.optjava.dz13.ant.ga.GPTreeConstructor;
import hr.fer.zemris.optjava.dz13.ant.ga.RampedHalfAndHalfInitializer;
import hr.fer.zemris.optjava.dz13.ant.ga.solution.AntEvaluator;
import hr.fer.zemris.optjava.dz13.ant.ga.solution.AntGPSolution;
import hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes.AbstractGPNode;
import hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes.AntIfFoodAheadNode;
import hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes.AntMoveNode;
import hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes.AntProg2Node;
import hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes.AntProg3Node;
import hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes.AntRotateLeftNode;
import hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes.AntRotateRightNode;
import hr.fer.zemris.optjava.dz13.ant.gui.AntSimulatorGUI;
import hr.fer.zemris.optjava.ga.GenerationGA;
import hr.fer.zemris.optjava.ga.IFunction;
import hr.fer.zemris.optjava.ga.generic.operators.ICross;
import hr.fer.zemris.optjava.ga.generic.operators.IMutation;
import hr.fer.zemris.optjava.ga.generic.operators.IPopulationInitializer;
import hr.fer.zemris.optjava.ga.generic.operators.ISelection;
import hr.fer.zemris.optjava.ga.generic.operators.impl.Tournament;

public class AntTrailGA {

    private static final int TOURNAMENT_SIZE = 7;
    private static final int KEEP_ELITE_N = 1;
    private static final int MAX_NODES = 1500;
    private static final int MAX_INIT_DEPT = 6;
    private static final int MAX_DEPTH = 14;
    private static final int MAX_STEPS = 600;
    private static final int PRINT_EVERY_N_ITER = 10;

    private static final double CROSS_CHANCE = 0.85;
    private static final double MUTATION_CHANCE = 0.14;

    private static List<AbstractGPNode> TERMINAL_NODES;
    private static List<AbstractGPNode> FUNCTION_NODES;

    static {
        TERMINAL_NODES = Arrays.asList(new AntMoveNode(), new AntRotateLeftNode(), new AntRotateRightNode());
        FUNCTION_NODES = Arrays.asList(new AntIfFoodAheadNode(), new AntProg2Node(), new AntProg3Node());
    }

    public static void main(final String[] args) throws IOException {
        String mapPath = "13-SantaFeAntTrail.txt";
        int maxIter = 100;
        int populationSize = 1000;
        int minFitness = 89;
        String dumpBestPath = "best.txt";

        if (args.length != 5) {
            System.err.println(
                    "Expected 5 arguments (map, maxIter, populationSize, minFitness, dumpPath), continuing using default arguments");

        } else {
            mapPath = args[0];
            maxIter = Integer.parseInt(args[1]);
            populationSize = Integer.parseInt(args[2]);
            minFitness = Integer.parseInt(args[3]);
            dumpBestPath = args[4];
        }
        AntMap map = AntMap.parseFile(mapPath);
        IFunction<AntGPSolution> f = new AntEvaluator(map, MAX_STEPS);
        GPTreeConstructor constructor = new GPTreeConstructor(FUNCTION_NODES, TERMINAL_NODES);
        ISelection<AntGPSolution> selection = new Tournament<>(TOURNAMENT_SIZE);
        IMutation<AntGPSolution> mutation = new AntMutation(MAX_NODES, MAX_DEPTH, MAX_INIT_DEPT, constructor);
        ICross<AntGPSolution> cross = new AntCrossover(MAX_NODES, MAX_DEPTH, constructor);

        IPopulationInitializer<AntGPSolution> initializer = new RampedHalfAndHalfInitializer(MAX_INIT_DEPT, MAX_NODES,
                constructor);

        List<AntGPSolution> population = initializer.initialize(populationSize);

        GenerationGA<AntGPSolution> ga = new GenerationGA<>(f, populationSize, minFitness, maxIter, selection, mutation,
                cross, KEEP_ELITE_N, MUTATION_CHANCE, CROSS_CHANCE);
        AntGPSolution best = ga.run(population, PRINT_EVERY_N_ITER);
        dumpSolution(best, dumpBestPath);
        AntSimulatorGUI.showSolution(map, best, minFitness, MAX_STEPS);

    }

    private static void dumpSolution(final AntGPSolution best, final String dumpBestPath) throws IOException {
        String tree = best.getData().serializeToString();
        FileWriter fw = new FileWriter(dumpBestPath);
        fw.write(tree);
        fw.close();

    }

}
