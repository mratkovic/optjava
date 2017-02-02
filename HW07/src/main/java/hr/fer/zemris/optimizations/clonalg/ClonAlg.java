package hr.fer.zemris.optimizations.clonalg;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optimizations.IFunction;

public class ClonAlg {
    List<AntiBody> population;
    int maxIterations;
    IFunction f;
    IFunction fitnessFunction;
    Random rnd;

    double wMin, wMax;
    int populationSize;
    double beta;
    int nNew;
    double ro;
    int mutateN;

    public ClonAlg(final IFunction f, final int populationSize, final double beta, final int nNew, final double ro,
            final int mutateN, final double wMin, final double wMax, final boolean minimize, final Random rnd) {
        super();
        this.f = f;
        this.wMin = wMin;
        this.wMax = wMax;
        this.populationSize = populationSize;
        this.population = AntiBody.newPopulation(populationSize, f, rnd, wMin, wMax);
        this.rnd = rnd;
        this.beta = beta;
        this.nNew = nNew;
        this.ro = ro;
        this.mutateN = mutateN;

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

    public double[] run(final int maxIter, final double errorLimit) {
        int iter = 0;
        evaluate(population);
        while (iter < maxIter) {

            List<AntiBody> nextPop = choose(population, populationSize);
            clone(nextPop);

            hipermutate(nextPop, rnd);
            evaluate(nextPop);

            List<AntiBody> tmpPop = choose(nextPop, populationSize);
            population = addNewAntibodies(tmpPop);
            evaluate(population);

            if (iter % 10 == 0) {
                System.out.printf("%6d iter\tcost: %4.6f\n", iter, population.get(0).value);
            }
            iter++;
        }
        return population.get(0).getSolution();

    }

    private List<AntiBody> addNewAntibodies(final List<AntiBody> pop) {
        List<AntiBody> pBirth = AntiBody.newPopulation(nNew, f, rnd, wMin, wMax);
        pop.sort((e1, e2) -> e2.compareTo(e1));
        pBirth.sort((e1, e2) -> e2.compareTo(e1));

        List<AntiBody> newpop = pop.subList(0, pop.size() - pBirth.size());
        newpop.addAll(pBirth);
        return newpop;
    }

    private void evaluate(final List<AntiBody> population) {
        for (AntiBody p : population) {
            p.value = f.valueAt(p.getSolution());
            p.fitness = fitnessFunction.valueAt(p.getSolution());
        }
        // desc
        population.sort((e1, e2) -> e2.compareTo(e1));
    }

    private static List<AntiBody> choose(final List<AntiBody> pop, final int n) {
        return pop.subList(0, n);
    }

    private void hipermutate(final List<AntiBody> pop, final Random rnd) {
        for (AntiBody a : pop) {
            for (int i = 0; i < a.getSolution().length; i++) {
                if (rnd.nextDouble() < Math.exp(-ro * a.fitness)) {
                    a.getSolution()[i] += rnd.nextGaussian();
                }
            }
        }
    }

    private void clone(final List<AntiBody> pop) {
        int n = pop.size();
        for (int i = 0; i < n; i++) {
            int nClones = (int) (beta * populationSize / (i + 1));
            for (int j = 0; j < nClones; j++) {
                pop.add(pop.get(i).clone());
            }
        }
    }

}
