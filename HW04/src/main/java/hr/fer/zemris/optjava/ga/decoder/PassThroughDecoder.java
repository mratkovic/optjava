package hr.fer.zemris.optjava.ga.decoder;

import hr.fer.zemris.optjava.ga.solution.DoubleArraySolution;

public class PassThroughDecoder implements IDecoder<DoubleArraySolution> {

    @Override
    public double[] decode(final DoubleArraySolution value) {
        return value.values;
    }

    @Override
    public void decode(final DoubleArraySolution value, double[] values) {
        values = value.values;
    }

    @Override
    public void printSolution(final DoubleArraySolution best) {
        System.out.println(best.values);
    }

}
