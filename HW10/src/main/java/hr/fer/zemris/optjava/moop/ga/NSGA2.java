package hr.fer.zemris.optjava.moop.ga;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.moop.cross.ICross;
import hr.fer.zemris.optjava.moop.mutation.IMutation;
import hr.fer.zemris.optjava.moop.problems.MOOPProblem;
import hr.fer.zemris.optjava.moop.selection.ISelection;
import hr.fer.zemris.optjava.moop.solution.DoubleArraySolution;

public class NSGA2<T extends DoubleArraySolution> {
    private static final double EPS = 1e-5;
    private static final double FITNESS_DECAY = 0.9;
    private static final String DECISION_FILE_PATH = "izlaz-dec.txt";
    private static final String OBJECTIVE_FILE_PARH = "izlaz-obj.txt";

    MOOPProblem problem;

    ISelection<T> selection;
    IMutation<T> mutation;
    ICross<T> cross;
    Random rnd;

    int printCnt = 1;
    int populationSize;

    public NSGA2(final MOOPProblem problem, final ISelection<T> selection, final IMutation<T> mutation,
            final ICross<T> cross, final Random rnd) {
        super();
        this.problem = problem;
        this.selection = selection;
        this.mutation = mutation;
        this.cross = cross;
        this.rnd = rnd;

    }

    public void run(List<T> population, final int maxIteration, final boolean dumpToFile) throws IOException {
        long startTime = System.nanoTime();
        populationSize = population.size();

        evaluatePopulation(population);

        int it = 0;
        while (it < maxIteration) {
            population = runSingleIteration(population);
            if (it % printCnt == 0) {
                List<List<T>> fronts = Util.nonDominantSort(population, problem);
                System.out.println(it + "\tNumber of fronts:" + fronts.size() + "; duration: "
                        + (System.nanoTime() - startTime) / 1e9 + "s");
            }

            ++it;

        }
        System.out.println("Iter:\t" + it);
        System.out.println("Time:" + (System.nanoTime() - startTime) / 1e9 + "s");
        evaluatePopulation(population);
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

    protected List<T> runSingleIteration(final List<T> population) {
        List<T> nextGen = new ArrayList<>();

        calcDistances(population);
        while (nextGen.size() < populationSize) {
            T p1 = selection.selectFromPopulation(population, rnd);
            T p2 = selection.selectFromPopulation(population, rnd);
            // cross
            List<T> children = cross.crossParents(p1, p2);
            children.forEach(c -> clipToDomain(c));

            // mutation, pick first child
            T bestChild = mutation.mutate(children.get(0));
            clipToDomain(bestChild);

            nextGen.add(bestChild);
        }
        evaluatePopulation(nextGen);
        population.addAll(nextGen);
        return calcNewPopulation(population);
    }

    private List<T> calcNewPopulation(final List<T> population) {
        List<List<T>> fronts = Util.nonDominantSort(population, problem);
        List<T> nextGen = new ArrayList<>();
        for (List<T> front : fronts) {
            if (front.size() + nextGen.size() < populationSize) {
                nextGen.addAll(front);

            } else {
                // last front
                calcDistances(population);
                front.sort(crowdingComparator);
                int need = populationSize - nextGen.size();

                List<T> selected = front.subList(0, need);
                nextGen.addAll(selected);
                break;
            }

        }
        return nextGen;
    }

    private void calcDistances(final List<T> population) {
        // calc domain range
        double[] mins = new double[problem.getNumberOfObjectives()];
        double[] maxs = new double[problem.getNumberOfObjectives()];
        Arrays.fill(mins, Double.POSITIVE_INFINITY);
        Arrays.fill(maxs, Double.NEGATIVE_INFINITY);

        for (T p : population) {
            for (int i = 0; i < problem.getNumberOfObjectives(); ++i) {
                maxs[i] = Math.max(maxs[i], p.objective[i]);
                mins[i] = Math.min(mins[i], p.objective[i]);
            }
            p.distance = 0;
        }

        // find neighbors for every component
        for (int i = 0; i < problem.getNumberOfObjectives(); ++i) {
            population.sort(new Util.ObjectiveDistanceComparator<T>(i));

            population.get(0).distance = Double.MAX_VALUE;
            population.get(populationSize - 1).distance = Double.MAX_VALUE;

            for (int j = 1; j < populationSize - 1; j++) {
                double lo = population.get(j - 1).objective[i];
                double hi = population.get(j + 1).objective[i];
                population.get(j).distance += (hi - lo) / (maxs[i] - mins[i]);
            }
        }
    }

    private void clipToDomain(final T d) {
        int n = problem.numberOfVariables();
        for (int i = 0; i < n; ++i) {
            d.values[i] = Math.max(d.values[i], problem.domainLowerBound()[i]);
            d.values[i] = Math.min(d.values[i], problem.domainUpperBound()[i]);
        }

    }

    private final Comparator<T> crowdingComparator = (p1, p2) -> -Double.compare(p1.distance, p2.distance);

    protected void evaluatePopulation(final List<T> population) {
        for (T x : population) {
            problem.evaluateSolution(x.values, x.objective);
        }
    }

}
