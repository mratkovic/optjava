package hr.fer.zemris.optjava.ga;

import java.util.Comparator;
import java.util.List;

import hr.fer.zemris.optjava.ga.generic.operators.ICross;
import hr.fer.zemris.optjava.ga.generic.operators.IMutation;
import hr.fer.zemris.optjava.ga.generic.operators.ISelection;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public abstract class AbstractGA<T extends AbstractGASolution> {
    private static final double EPS = 1e-5;

    IFunction<T> f;
    int populationSize;
    double minValue;

    int maxIteration;
    ISelection<T> selection;
    IMutation<T> mutation;
    ICross<T> cross;

    IRNG rnd;

    T best;
    int printCnt;

    public AbstractGA(final IFunction<T> f, final int populationSize, final double minValue, final int maxIteration,
            final ISelection<T> selection, final IMutation<T> mutation, final ICross<T> cross) {
        super();
        this.f = f;
        this.populationSize = populationSize;
        this.minValue = minValue;
        this.maxIteration = maxIteration;
        this.selection = selection;
        this.mutation = mutation;
        this.cross = cross;
        rnd = RNG.getRNG();
    }

    public T run(List<T> population, final int printCnt) {
        this.printCnt = printCnt;
        long startTime = System.nanoTime();
        evaluatePopulation(population);

        best = population.get(0);

        int it = 0;
        while (it < maxIteration && best.fitness < minValue) {
            population = runSingleIteration(population);
            T newBest = population.stream().min(descComparator).orElse(null);

            printStatus(newBest, it);
            best = newBest;

            ++it;

        }
        System.out.println("Iter:\t" + it);
        System.out.println("Best value: " + best.fitness);
        System.out.println("Time:" + (System.nanoTime() - startTime) / 1e9 + "s");
        return best;
    }

    private void printStatus(final T newBest, final int it) {
        if (it % printCnt == 0) {
            System.out.println(it + "\t\tBest ->  Fitness: " + best.fitness);
        }

    }

    protected abstract List<T> runSingleIteration(final List<T> population);

    private final Comparator<T> descComparator = (p1, p2) -> -Double.compare(p1.fitness, p2.fitness);

    protected void evaluatePopulation(final List<T> population) {
        for (T sol : population) {
            evaluate(sol);
        }
        population.sort(descComparator);
    }

    protected void evaluate(final T sol) {
        sol.fitness = f.valueAt(sol);
    }
}
