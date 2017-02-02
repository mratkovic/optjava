package hr.fer.zemris.optjava.dz3.solution.bit;

public class NaturalBinaryDecoder extends BitvectorDecoder {

    public NaturalBinaryDecoder(final double min, final double max, final int bit, final int n) {
        super(min, max, bit, n);
    }

    public NaturalBinaryDecoder(final double[] mins, final double[] maxs, final int[] bits, final int n) {
        super(mins, maxs, bits, n);
    }

    @Override
    public double[] decode(final BitvectorSolution value) {
        double[] values = new double[n];
        decode(value, values);
        return values;
    }

    @Override
    public void decode(final BitvectorSolution value, final double[] values) {
        if (values.length != n) {
            throw new IllegalArgumentException("Invalid array size");
        }
        for (int i = 0, current = 0; i < getDimensions(); ++i) {
            int k = 0, len = bits[i];
            for (int j = 0; j < bits[i]; ++j, ++current) {
                k += value.bits[current] << (len - 1 - j);
            }

            double domain = maxs[i] - mins[i];
            double range = Math.pow(2, bits[i]);
            values[i] = mins[i] + (k / range) * domain;
        }

    }

}
