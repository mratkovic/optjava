package hr.fer.zemris.optjava.dz5.part1;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.function.IFunction;
import hr.fer.zemris.optjava.ga.cross.ICross;
import hr.fer.zemris.optjava.ga.mutation.IMutation;
import hr.fer.zemris.optjava.ga.selection.ISelection;
import hr.fer.zemris.optjava.ga.solution.SingleObjectiveSolution;

public class RAPGA<T extends SingleObjectiveSolution> {

    private static final int MAX_ITER = 10000;
    IFunction<T> f;
    IFunction<T> fitnessFunction;

    int minPopulation;
    int maxPopulation;
    int maxSelectionPressure;

    double compFactor;
    double compFactorStep;
    int compFactorIterChange;

    double successRatio;

    ISelection<T> p1Selection;
    ISelection<T> p2Selection;
    IMutation<T> mutation;
    ICross<T> cross;

    Random rnd;
    T best;

    public RAPGA(final IFunction<T> f, final int minPopulation, final int maxPopulation, final int maxSelectionPressure,
            final ISelection<T> p1Selection, final ISelection<T> p2Selection, final IMutation<T> mutation,
            final ICross<T> cross, final double successRatio, final int compFactorUpdateIter,
            final double compFactorStep, final Random rnd, final boolean minimize) {
        super();
        this.f = f;
        this.minPopulation = minPopulation;
        this.maxPopulation = maxPopulation;
        this.maxSelectionPressure = maxSelectionPressure;
        this.p1Selection = p1Selection;
        this.p2Selection = p2Selection;
        this.mutation = mutation;
        this.cross = cross;
        this.rnd = rnd;
        this.successRatio = successRatio;
        this.fitnessFunction = values -> {
            int a = minimize ? -1 : 1;
            return a * f.valueAt(values);
        };

        this.compFactorIterChange = compFactorUpdateIter;
        this.compFactorStep = compFactorStep;

    }

    public List<T> runPopulation(final List<T> population) {
        evaluatePopulation(population);
        best = population.get(0);

        double selectionPressure = 0.0;
        compFactor = compFactorStep;
        int it = 0;

        while (it < MAX_ITER && selectionPressure < maxSelectionPressure) {
            it++;

            int popSize = population.size();
            HashSet<T> newpop = new HashSet<>();
            HashSet<T> pool = new HashSet<>();
            newpop.add(best);

            while (newpop.size() < popSize * successRatio
                    && (newpop.size() + pool.size()) < popSize * maxSelectionPressure) {
                T p1 = p1Selection.selectFromPopulation(population);
                T p2 = p2Selection.selectFromPopulation(population);

                T child = cross.crossParents(p1, p2);
                child = mutation.mutate(child);
                evaluateIndividual(child);

                if (checkKeepCondition(child, p1, p2, compFactor)) {
                    newpop.add(child);
                } else {
                    pool.add(child);
                }
            }

            selectionPressure = (newpop.size() + pool.size()) / (double) popSize;
            updatePopulation(population, newpop, pool, true);

            if (it % compFactorIterChange == 0) {
                compFactor = Math.min(1.0, compFactor + compFactorStep);
            }

        }
        System.out.println("Best fitness: " + best.fitness);
        System.out.println();
        return population;

    }

    // from pdf implementation
    public List<T> run(final List<T> population) {
        long startTime = System.nanoTime();
        evaluatePopulation(population);
        best = population.get(0);

        double selectionPressure = 0.0;
        compFactor = compFactorStep;
        int maxEffort = maxPopulation * maxSelectionPressure;
        int it = 0;

        while (it < MAX_ITER && selectionPressure < maxSelectionPressure) {
            it++;
            int popSize = population.size();
            HashSet<T> newpop = new HashSet<>();
            HashSet<T> pool = new HashSet<>();

            int effort = 0;
            while (effort < maxEffort && newpop.size() < maxPopulation) {
                effort++;
                T p1 = p1Selection.selectFromPopulation(population);
                T p2 = p2Selection.selectFromPopulation(population);

                T child = cross.crossParents(p1, p2);
                child = mutation.mutate(child);
                evaluateIndividual(child);

                if (checkKeepCondition(child, p1, p2, compFactor)) {
                    newpop.add(child);
                } else {
                    pool.add(child);
                }
            }

            selectionPressure = (newpop.size() + pool.size()) / (double) popSize;
            updatePopulation(population, newpop, pool, true);

            if (it % compFactorIterChange == 0) {
                compFactor = Math.min(1.0, compFactor + compFactorStep);
            }

        }

        System.out.println();
        System.out.println(best.toString());
        System.out.println("Num. of gen:" + it);
        System.out.println("Best fitness: " + best.fitness);
        System.out.println("Time:" + (System.nanoTime() - startTime) / 1e9 + "s");
        return population;

    }

    private void updatePopulation(final List<T> population, final HashSet<T> newpop, final HashSet<T> pool,
            final boolean print) {
        population.clear();
        population.addAll(newpop);
        Iterator<T> iter = pool.iterator();
        while (population.size() < minPopulation && iter.hasNext()) {
            population.add(iter.next());
        }
        evaluatePopulation(population);

        T newbest = population.get(0);
        if (newbest.fitness > best.fitness) {
            best = newbest;
            if (print) {
                System.out.println("current best:" + best.fitness);
            }
        }
    }

    private boolean checkKeepCondition(final T child, final T p1, final T p2, final double compFactor) {
        double lo = Math.min(p1.fitness, p2.fitness);
        double hi = Math.max(p1.fitness, p2.fitness);

        double limit = lo + compFactor * (hi - lo);
        return child.fitness >= limit;
    }

    private final Comparator<T> descComparator = (p1, p2) -> Double.compare(p2.fitness, p1.fitness);

    protected T findBest(final List<T> population) {
        evaluatePopulation(population);
        return population.get(0);
    }

    protected void evaluateIndividual(final T individual) {
        individual.value = f.valueAt(individual);
        individual.fitness = fitnessFunction.valueAt(individual);
    }

    public void evaluatePopulation(final List<T> population) {
        for (T sol : population) {
            sol.value = f.valueAt(sol);
            sol.fitness = fitnessFunction.valueAt(sol);
        }
        population.sort(descComparator);
    }
}
