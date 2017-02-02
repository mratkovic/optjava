package hr.fer.zemris.optjava.ga.selection;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.ga.solution.SingleObjectiveSolution;

public class RandomSelection<T extends SingleObjectiveSolution> implements ISelection<T> {
    Random rnd;

    public RandomSelection(final Random rnd) {
        super();
        this.rnd = rnd;
    }

    @Override
    public T selectFromPopulation(final List<T> population) {
        int n = population.size();
        int i = rnd.nextInt(n);
        return population.get(i);
    }

}
