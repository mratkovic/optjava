package hr.fer.zemris.optimizations.clonalg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optimizations.IFunction;
import hr.fer.zemris.optimizations.SingleObjectiveSolution;

public class AntiBody extends SingleObjectiveSolution {

    private final double[] solution;

    public AntiBody(final int size) {
        solution = new double[size];
    }

    public AntiBody(final double[] arr) {
        this.solution = arr;
    }

    public void randomize(final double[] wMin, final double[] wMax, final Random rnd) {
        for (int i = 0; i < wMax.length; i++) {
            solution[i] = rnd.nextDouble() * (wMax[i] - wMin[i]) + wMin[i];
        }
    }

    public double[] getSolution() {
        return solution;
    }

    @Override
    public AntiBody clone() {
        return new AntiBody(Arrays.copyOf(solution, solution.length));
    }

    public static List<AntiBody> newPopulation(final int size, final IFunction problem, final Random rnd,
            final double[] xMin, final double[] xMax) {

        List<AntiBody> pop = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            AntiBody a = new AntiBody(problem.nVariables());
            a.randomize(xMin, xMax, rnd);
            pop.add(a);
        }
        return pop;
    }

    public static List<AntiBody> newPopulation(final int size, final IFunction problem, final Random rnd,
            final double xMin, final double xMax) {

        int n = problem.nVariables();
        double[] xMins = new double[n];
        double[] xMaxs = new double[n];
        Arrays.fill(xMins, xMin);
        Arrays.fill(xMaxs, xMax);
        return newPopulation(size, problem, rnd, xMins, xMaxs);
    }

    @Override
    public SingleObjectiveSolution duplicate() {
        SingleObjectiveSolution ret = new AntiBody(solution);
        ret.fitness = this.fitness;
        ret.value = this.value;
        return ret;
    }

}
