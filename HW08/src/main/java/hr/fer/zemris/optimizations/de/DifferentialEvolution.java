package hr.fer.zemris.optimizations.de;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optimizations.DoubleArraySolution;
import hr.fer.zemris.optimizations.IFunction;

public class DifferentialEvolution {
    IDiferentialEvolutionStrategy strategy;
    int nLinearCombinations;
    List<DoubleArraySolution> population;
    DoubleArraySolution best;
    int maxIterations;
    IFunction f;
    IFunction fitnessFunction;
    Random rnd;

    double wMin, wMax;
    int populationSize;
    double crossChance;

    public DifferentialEvolution(final IDiferentialEvolutionStrategy strategy, final IFunction f,
            final int populationSize, final double crossChance, final double wMin, final double wMax,
            final boolean minimize, final Random rnd) {
        super();
        this.f = f;
        this.strategy = strategy;
        this.wMin = wMin;
        this.wMax = wMax;
        this.populationSize = populationSize;
        this.crossChance = crossChance;
        this.rnd = rnd;

        this.fitnessFunction = new IFunction() {
            @Override
            public double valueAt(final double[] values) {
                int a = minimize ? -1 : 1;
                return a * f.valueAt(values);
            }

            @Override
            public int nVariables() {
                return f.nVariables();
            }
        };
    }

    private void initializePopulation() {
        this.population = new ArrayList<>();
        for (int i = 0; i < populationSize; ++i) {
            DoubleArraySolution s = new DoubleArraySolution(f.nVariables());
            s.randomize(rnd, wMin, wMax);
            population.add(s);
        }
    }

    private void sortPopulation() {
        population.sort((e1, e2) -> e2.compareTo(e1));
    }

    private void evaluate() {
        population.forEach(p -> eval(p));
    }

    private void eval(final DoubleArraySolution p) {
        p.value = f.valueAt(p.values);
        p.fitness = fitnessFunction.valueAt(p.values);
    }

    private DoubleArraySolution cross(final DoubleArraySolution old, final DoubleArraySolution mutated) {
        int rndIndex = rnd.nextInt(f.nVariables());

        DoubleArraySolution newSol = old.duplicate();
        for (int j = 0; j < f.nVariables(); ++j) {
            if (rnd.nextDouble() < crossChance || j == rndIndex) {
                newSol.values[j] = mutated.values[j];
            }
        }

        // eval new
        eval(newSol);
        return newSol;
    }

    private void findBest() {
        DoubleArraySolution newBest = population.stream().max((e1, e2) -> Double.compare(e1.fitness, e2.fitness))
                .orElse(null);
        if (best == null || newBest.fitness > best.fitness) {
            best = newBest;
        }
    }

    public double[] run(final int maxIter, final double errorLimit) {
        int iter = 0;

        initializePopulation();
        evaluate();
        findBest();

        while (iter < maxIter && best.value > errorLimit) {

            List<DoubleArraySolution> nextPopulation = new ArrayList<>();
            for (int i = 0; i < populationSize; ++i) {
                DoubleArraySolution old = population.get(i);
                DoubleArraySolution mutated = strategy.mutate(i, population);
                DoubleArraySolution newSol = cross(old, mutated);
                nextPopulation.add(newSol.fitness > old.fitness ? newSol : old);
            }
            population = nextPopulation;
            findBest();
            if (iter % 10 == 0) {
                System.out.printf("%6d iter\tcost: %4.6f\n", iter, best.value);
            }
            ++iter;

        }
        return best.values;

    }

}
