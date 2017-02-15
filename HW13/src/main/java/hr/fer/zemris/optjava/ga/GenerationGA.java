package hr.fer.zemris.optjava.ga;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.ga.generic.operators.ICross;
import hr.fer.zemris.optjava.ga.generic.operators.IMutation;
import hr.fer.zemris.optjava.ga.generic.operators.ISelection;

public class GenerationGA<T extends AbstractGASolution> extends AbstractGA<T> {

    private final int keepEliteN;
    double mutationChance;
    double crossChance;

    public GenerationGA(final IFunction<T> f, final int populationSize, final double minValue, final int maxIteration,
            final ISelection<T> selection, final IMutation<T> mutation, final ICross<T> cross, final int keepEliteN,
            final double mutationChance, final double crossChance) {
        super(f, populationSize, minValue, maxIteration, selection, mutation, cross);
        this.keepEliteN = keepEliteN;
        this.mutationChance = mutationChance;
        this.crossChance = crossChance;
    }

    @Override
    protected List<T> runSingleIteration(List<T> population) {
        List<T> nextGen = new ArrayList<>();

        for (int i = 0; i < keepEliteN; ++i) {
            nextGen.add(population.get(i));
        }

        while (nextGen.size() < populationSize) {
            double prob = rnd.nextDouble();
            T p1 = selection.selectFromPopulation(population);
            T child = null;

            if (prob <= mutationChance) {
                child = mutation.mutate(p1);

            } else if (prob <= mutationChance + crossChance) {
                T p2 = selection.selectFromPopulation(population);
                child = cross.crossParents(p1, p2);

            } else {
                child = p1;
            }
            evaluate(child);
            child.adjustFitness();

            nextGen.add(child);
        }

        population.clear();
        population = nextGen;
        evaluatePopulation(population);
        return population;
    }

}
