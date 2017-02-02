package hr.fer.zemris.optjava.ga.solution;

import java.util.Arrays;
import java.util.Random;

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

    @Override
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

    /** Array size. */
    public int size() {
        return values.length;
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

}
