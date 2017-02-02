package hr.fer.zemris.optjava.moop.solution;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.moop.problems.MOOPProblem;

public class DoubleArraySolution extends MultiObjectiveSolution {
    public double[] values;

    public DoubleArraySolution(final int nObjective, final int size) {
        super(nObjective);
        this.values = new double[size];
    }

    public DoubleArraySolution newLikeThis() {
        return new DoubleArraySolution(objective.length, values.length);
    }

    @Override
    public DoubleArraySolution duplicate() {
        DoubleArraySolution d = new DoubleArraySolution(objective.length, values.length);
        d.values = Arrays.copyOf(values, values.length);
        return d;
    }

    /**
     * Assigns random values for each element v_i generated from interval
     * [first_i, second_i]
     *
     * @param random
     * @param first
     * @param second
     */
    public void randomize(final Random random, final double[] first, final double[] second) {

        for (int i = 0; i < values.length; i++) {
            double min = Math.min(first[i], second[i]);
            double delta = Math.abs(first[i] - second[i]);

            values[i] = random.nextDouble() * delta + min;
        }
    }

    /**
     * Assigns random values for each element v_i generated from interval
     * [first, second]
     *
     * @param random
     * @param first
     * @param second
     */
    public void randomize(final Random random, final double first, final double second) {

        for (int i = 0; i < values.length; i++) {
            double min = Math.min(first, second);
            double delta = Math.abs(first - second);

            values[i] = random.nextDouble() * delta + min;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(values);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DoubleArraySolution other = (DoubleArraySolution) obj;
        if (!Arrays.equals(values, other.values)) {
            return false;
        }
        return true;
    }

    public int size() {
        return values.length;
    }

    public boolean dominatesOver(final DoubleArraySolution xj, final MOOPProblem problem) {
        int nObjectives = problem.getNumberOfObjectives();
        boolean better = false;

        for (int i = 0; i < nObjectives; ++i) {
            if (objective[i] < xj.objective[i]) {
                better = true;
            } else if (objective[i] > xj.objective[i]) {
                return false;
            }
        }

        return better;
    }

    @Override
    public String toString() {
        return Arrays.toString(objective);
    }

}
