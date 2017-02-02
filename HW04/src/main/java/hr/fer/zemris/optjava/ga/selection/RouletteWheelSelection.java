package hr.fer.zemris.optjava.ga.selection;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.ga.solution.SingleObjectiveSolution;

public class RouletteWheelSelection<T extends SingleObjectiveSolution> implements ISelection<T> {

    @SuppressWarnings("unchecked")
    @Override
    public T selectFromPopulation(final List<T> population, final Random rnd) {
        double best = population.get(0).fitness;
        double totalFitness = 0;
        double[] fitness = new double[population.size()];

        for (int i = 0; i < fitness.length; ++i) {
            fitness[i] = 1.0 / (Math.abs(best - population.get(i).fitness) + 1);
            totalFitness += fitness[i];
        }

        double value = rnd.nextDouble() * totalFitness;
        double cumulativeFittnes = 0;
        for (int i = 0; i < fitness.length; ++i) {
            cumulativeFittnes += fitness[i];
            if (value <= cumulativeFittnes) {
                return (T) population.get(i).duplicate();
            }
        }
        return (T) population.get(population.size() - 1).duplicate();
    }

}
