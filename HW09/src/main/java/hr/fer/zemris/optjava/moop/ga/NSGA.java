package hr.fer.zemris.optjava.moop.ga;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.moop.cross.ICross;
import hr.fer.zemris.optjava.moop.distance.IDistance;
import hr.fer.zemris.optjava.moop.mutation.IMutation;
import hr.fer.zemris.optjava.moop.problems.MOOPProblem;
import hr.fer.zemris.optjava.moop.selection.ISelection;
import hr.fer.zemris.optjava.moop.solution.DoubleArraySolution;

public class NSGA<T extends DoubleArraySolution> {
    private static final double EPS = 1e-5;
    private static final double FITNESS_DECAY = 0.9;
    private static final String DECISION_FILE_PATH = "izlaz-dec.txt";
    private static final String OBJECTIVE_FILE_PARH = "izlaz-obj.txt";

    MOOPProblem problem;

    ISelection<T> selection;
    IMutation<T> mutation;
    ICross<T> cross;
    IDistance<T> dist;
    Random rnd;

    private final double sigmaShare;
    private final double alpha;

    T best;
    int printCnt = 1;
    int populationSize;

    public NSGA(final MOOPProblem problem, final ISelection<T> selection, final IMutation<T> mutation,
            final ICross<T> cross, final IDistance<T> dist, final Random rnd, final double sigmaShare,
            final double alpha) {
        super();
        this.problem = problem;
        this.selection = selection;
        this.mutation = mutation;
        this.cross = cross;
        this.dist = dist;
        this.rnd = rnd;
        this.sigmaShare = sigmaShare;
        this.alpha = alpha;
    }

    public void run(List<T> population, final int maxIteration, final boolean dumpToFile) throws IOException {
        long startTime = System.nanoTime();
        populationSize = population.size();
        evaluatePopulation(population);

        best = population.get(0);

        int it = 0;
        while (it < maxIteration) {
            population = runSingleIteration(population);
            best = population.get(0);
            if (it % printCnt == 0) {
                System.out.println(it + "\t\tBest ->  Costs: " + best);
            }

            ++it;

        }
        System.out.println("Iter:\t" + it);
        System.out.println("Best value: " + best);
        System.out.println("Time:" + (System.nanoTime() - startTime) / 1e9 + "s");
        printStates(population);

        if (dumpToFile) {
            dumpPopulationToFiles(population);
        }

    }

    private void dumpPopulationToFiles(final List<T> population) throws IOException {
        FileWriter fwDec = new FileWriter(DECISION_FILE_PATH);
        FileWriter fwObj = new FileWriter(OBJECTIVE_FILE_PARH);
        for (T x : population) {
            String vals = Arrays.toString(x.values);
            String fs = Arrays.toString(x.objective);
            fwDec.write(vals + "\n");
            fwObj.write(fs + "\n");
        }

        fwDec.close();
        fwObj.close();
        System.out.println("Data dumped to file");
    }

    private void printStates(final List<T> population) {
        List<List<T>> fronts = Util.nonDominantSort(population, problem);
        System.out.println("Number of fronts:" + fronts.size());
        int cnt = 0;
        for (List<T> front : fronts) {
            System.out.println("\tFront " + cnt + ";\t#ofSolutions:" + front.size());
            cnt++;

        }

    }

    protected List<T> runSingleIteration(List<T> population) {
        List<T> nextGen = new ArrayList<>();
        while (nextGen.size() < populationSize) {
            T p1 = selection.selectFromPopulation(population, rnd);
            T p2 = selection.selectFromPopulation(population, rnd);

            // cross
            List<T> children = cross.crossParents(p1, p2);
            children.forEach(c -> clipToDomain(c));

            // mutation
            T bestChild = mutation.mutate(findBest(children));
            clipToDomain(bestChild);

            nextGen.add(bestChild);
        }
        population.clear();
        population = nextGen;
        evaluatePopulation(population);
        return population;
    }

    private void clipToDomain(final T d) {

        int n = problem.numberOfVariables();
        for (int i = 0; i < n; ++i) {
            d.values[i] = Math.max(d.values[i], problem.domainLowerBound()[i]);
            d.values[i] = Math.min(d.values[i], problem.domainUpperBound()[i]);
        }

    }

    protected T findBest(final List<T> population) {
        evaluatePopulation(population);
        return population.get(0);
    }

    private final Comparator<T> descComparator = (p1, p2) -> -Double.compare(p1.fitness, p2.fitness);

    protected void evaluatePopulation(final List<T> population) {
        for (T x : population) {
            problem.evaluateSolution(x.values, x.objective);
        }
        List<List<T>> fronts = Util.nonDominantSort(population, problem);

        double fMin = population.size();
        for (List<T> front : fronts) {
            double currentMin = Double.MAX_VALUE;
            for (T xi : front) {
                double nci = front.stream().mapToDouble(xj -> sh(xi, xj)).sum();
                double fitness = fMin / nci;
                xi.fitness = fitness;
                currentMin = Math.min(currentMin, fitness);
            }
            fMin = currentMin * FITNESS_DECAY;
        }
        population.sort(descComparator);
    }

    private double sh(final T xi, final T xj) {
        double distance = dist.calcDist(xi, xj);
        if (distance < sigmaShare) {
            return 1 - Math.pow(distance / sigmaShare, alpha);
        }
        return 0;
    }
}
