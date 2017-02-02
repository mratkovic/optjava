package hr.fer.zemris.optjava.moop.ga;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.optjava.moop.problems.MOOPProblem;
import hr.fer.zemris.optjava.moop.solution.DoubleArraySolution;

public class Util {
    public static <T extends DoubleArraySolution> List<List<T>> nonDominantSort(final List<T> population,
            final MOOPProblem problem) {

        HashMap<T, HashSet<T>> dominates = new HashMap<>();
        HashMap<T, Integer> dominatedBy = new HashMap<>();

        for (T d : population) {
            dominates.put(d, new HashSet<T>());
            dominatedBy.put(d, 0);
        }
        int n = population.size();
        for (int i = 0; i < n; ++i) {
            T xi = population.get(i);

            for (int j = 0; j < n; ++j) {
                if (i == j) {
                    continue;
                }
                T xj = population.get(j);
                if (xi.dominatesOver(xj, problem)) {
                    dominates.get(xi).add(xj);
                    int newVal = dominatedBy.get(xj) + 1;
                    dominatedBy.put(xj, newVal);
                }

            }
        }

        List<List<T>> fronts = new LinkedList<>();
        while (true) {
            List<T> front = getZeroFront(population, dominates, dominatedBy);
            if (front.isEmpty()) {
                break;
            }
            fronts.add(front);
            for (T xi : front) {
                dominatedBy.put(xi, -1); // mark as taken
                for (T xj : dominates.get(xi)) {
                    int newValue = dominatedBy.get(xj) - 1;
                    dominatedBy.put(xj, newValue);
                }
            }
        }
        return fronts;
    }

    private static <T extends DoubleArraySolution> List<T> getZeroFront(final List<T> population,
            final HashMap<T, HashSet<T>> dominates, final HashMap<T, Integer> dominatedBy) {
        List<T> front = new LinkedList<>();
        for (T p : population) {
            if (dominatedBy.get(p) == 0) {
                front.add(p);
            }
        }
        return front;
    }

}
