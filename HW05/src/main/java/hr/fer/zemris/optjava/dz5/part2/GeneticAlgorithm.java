package hr.fer.zemris.optjava.dz5.part2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz5.part1.RAPGA;
import hr.fer.zemris.optjava.function.QuadraticAssigmentProblem;
import hr.fer.zemris.optjava.ga.cross.ICross;
import hr.fer.zemris.optjava.ga.cross.OX2Cross;
import hr.fer.zemris.optjava.ga.mutation.IMutation;
import hr.fer.zemris.optjava.ga.mutation.SwapMutation;
import hr.fer.zemris.optjava.ga.selection.ISelection;
import hr.fer.zemris.optjava.ga.selection.RandomSelection;
import hr.fer.zemris.optjava.ga.selection.Tournament;
import hr.fer.zemris.optjava.ga.solution.Permutation;

public class GeneticAlgorithm {

    public static void main(final String[] args) throws IOException {
        /** All params */
        int populationSize = 300;
        int nSubpopulation = 10;
        String path = "./samples/els19.dat";

        int minPopulation = 10;
        int maxPopulation = 120;
        int maxSelectionPressure = 400;
        double successRatio = 0.75;
        int compFactorUpdateIter = 1000;
        double compFactorStep = 0.01;
        int tournamentSize = 5;
        Random rnd = new Random();

        ISelection<Permutation> p1Selection = new Tournament<>(tournamentSize, rnd);
        ISelection<Permutation> p2Selection = new RandomSelection<>(rnd);
        IMutation<Permutation> mutation = new SwapMutation(rnd);
        ICross<Permutation> cross = new OX2Cross(rnd);
        QuadraticAssigmentProblem f = new QuadraticAssigmentProblem(path);

        // run algo
        RAPGA<Permutation> rapga = new RAPGA<>(f, minPopulation, maxPopulation, maxSelectionPressure, p1Selection,
                p2Selection, mutation, cross, successRatio, compFactorUpdateIter, compFactorStep, rnd, true);

        SASEGASA<Permutation> ga = new SASEGASA<>(nSubpopulation, rnd, rapga);

        List<Permutation> population = initialPopulation(f.getSize(), populationSize, rnd);
        ga.run(population);

    }

    private static List<Permutation> initialPopulation(final int n, final int size, final Random rnd) {
        HashSet<Permutation> population = new HashSet<>();
        while (population.size() < size) {
            Permutation bv = new Permutation(n);
            bv.randomize(rnd);
            population.add(bv);
        }
        return new ArrayList<>(population);
    }
}
