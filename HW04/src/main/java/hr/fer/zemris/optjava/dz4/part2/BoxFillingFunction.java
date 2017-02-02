package hr.fer.zemris.optjava.dz4.part2;

import hr.fer.zemris.optjava.dz4.function.IFunction;

public class BoxFillingFunction implements IFunction {

    /**
     * Value[i] represents distance between height of column i and max height.
     * Function value is mean square value of unused space.
     *
     */
    @Override
    public double valueAt(final double[] values) {
        double cost = 0;
        for (double current : values) {
            cost += Math.pow(current, 2);
        }

        cost /= values.length;
        return -cost;
    }

}
