package hr.fer.zemris.optjava.dz3.solution.doublearray;

import hr.fer.zemris.optjava.dz3.solution.IDecoder;

public class PassThroughDecoder implements IDecoder<DoubleArraySolution> {

    @Override
    public double[] decode(final DoubleArraySolution value) {
        return value.values;
    }

    @Override
    public void decode(final DoubleArraySolution value, double[] values) {
        values = value.values;
    }

}
