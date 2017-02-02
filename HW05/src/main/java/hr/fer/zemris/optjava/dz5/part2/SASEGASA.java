package hr.fer.zemris.optjava.dz5.part2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz5.part1.RAPGA;
import hr.fer.zemris.optjava.ga.solution.SingleObjectiveSolution;

public class SASEGASA<T extends SingleObjectiveSolution> {
    int nPopulations;
    Random rnd;
    RAPGA<T> offspringSelection;

    public SASEGASA(final int nPopulations, final Random rnd, final RAPGA<T> offspringSelection) {
        super();
        this.nPopulations = nPopulations;
        this.rnd = rnd;
        this.offspringSelection = offspringSelection;
    }

    public void run(final List<T> population) {
        long startTime = System.nanoTime();

        while (nPopulations > 0) {
            HashSet<T> newPopulation = new HashSet<>();

            int totalPopulationSize = population.size();
            int popSize = totalPopulationSize / nPopulations;

            int start = 0;
            for (int i = 0; i < nPopulations; ++i) {
                int size = popSize;
                if (i == nPopulations - 1) {
                    size = totalPopulationSize - start;
                }
                HashSet<T> subpopulation = new HashSet<>(population.subList(start, start + size));
                System.out.println("Eval subpopulation: " + i);
                newPopulation.addAll(offspringSelection.runPopulation(new ArrayList<>(subpopulation)));
                start += size;
            }
            population.clear();
            population.addAll(newPopulation);
            nPopulations--;
        }

        offspringSelection.evaluatePopulation(population);
        T best = population.get(0);

        System.out.println();
        System.out.println(best.toString());
        System.out.println("Best fitness: " + best.fitness);
        System.out.println("Time:" + (System.nanoTime() - startTime) / 1e9 + "s");

    }

}
