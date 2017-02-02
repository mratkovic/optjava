package hr.fer.zemris.optjava.dz5.part1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.function.IFunction;
import hr.fer.zemris.optjava.function.MaxOnesFunction;
import hr.fer.zemris.optjava.ga.cross.ICross;
import hr.fer.zemris.optjava.ga.cross.SinglePointCross;
import hr.fer.zemris.optjava.ga.mutation.IMutation;
import hr.fer.zemris.optjava.ga.mutation.ToggleBitMutation;
import hr.fer.zemris.optjava.ga.selection.ISelection;
import hr.fer.zemris.optjava.ga.selection.RandomSelection;
import hr.fer.zemris.optjava.ga.selection.Tournament;
import hr.fer.zemris.optjava.ga.solution.BitVectorSolution;

public class GeneticAlgorithm {

    public static void main(final String[] args) {
        /** All params */
        int minPopulation = 10;
        int maxPopulation = 250;
        int n = 100;
        if (args.length == 1) {
            n = Integer.parseInt(args[0]);
        }
        int maxSelectionPressure = 50;
        double successRatio = 0.85;
        int compFactorUpdateIter = 100000;
        double compFactorStep = 0.2;
        int tournamentSize = 5;

        Random rnd = new Random();

        ISelection<BitVectorSolution> p1Selection = new Tournament<>(tournamentSize, rnd);
        ISelection<BitVectorSolution> p2Selection = new RandomSelection<>(rnd);
        IMutation<BitVectorSolution> mutation = new ToggleBitMutation(rnd);
        ICross<BitVectorSolution> cross = new SinglePointCross(rnd);
        IFunction<BitVectorSolution> f = new MaxOnesFunction();

        // run algo
        RAPGA<BitVectorSolution> rapga = new RAPGA<>(f, minPopulation, maxPopulation, maxSelectionPressure, p1Selection,
                p2Selection, mutation, cross, successRatio, compFactorUpdateIter, compFactorStep, rnd, false);

        List<BitVectorSolution> population = initialPopulation(n, minPopulation, rnd);
        rapga.run(population);

    }

    private static List<BitVectorSolution> initialPopulation(final int n, final int size, final Random rnd) {
        HashSet<BitVectorSolution> population = new HashSet<>();
        while (population.size() < size) {
            BitVectorSolution bv = new BitVectorSolution(n);
            bv.randomize(rnd);
            population.add(bv);
        }
        return new ArrayList<>(population);
    }

}
