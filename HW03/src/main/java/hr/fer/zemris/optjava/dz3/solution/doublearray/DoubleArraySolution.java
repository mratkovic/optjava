package hr.fer.zemris.optjava.dz3.solution.doublearray;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

public class DoubleArraySolution extends SingleObjectiveSolution {

    public double[] values;

    public DoubleArraySolution(final int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Invalid size argument");
        }

        this.values = new double[size];
    }

    public DoubleArraySolution newLikeThis() {
        return new DoubleArraySolution(values.length);
    }

    public DoubleArraySolution duplicate() {
        DoubleArraySolution d = new DoubleArraySolution(values.length);
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

}
