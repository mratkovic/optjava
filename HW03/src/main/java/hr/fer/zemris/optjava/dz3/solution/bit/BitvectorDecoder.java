package hr.fer.zemris.optjava.dz3.solution.bit;

import java.util.Arrays;

import hr.fer.zemris.optjava.dz3.solution.IDecoder;

public abstract class BitvectorDecoder implements IDecoder<BitvectorSolution> {

    /** Domain for each variable. */
    protected double[] mins;
    protected double[] maxs;
    /** Number of bits for each variable. */
    protected int[] bits;
    /** Number of variables (dimension). */
    protected int n;
    /** Total number of bits in this representation. */
    protected int totalBits;

    public BitvectorDecoder(final double[] mins, final double[] maxs, final int[] bits, final int n) {
        this.mins = mins;
        this.maxs = maxs;
        this.bits = bits;
        this.n = n;

        Arrays.stream(bits).forEach(b -> totalBits += b);
    }

    public BitvectorDecoder(final double min, final double max, final int bit, final int n) {
        this.n = n;

        mins = new double[n];
        maxs = new double[n];
        bits = new int[n];
        Arrays.fill(mins, min);
        Arrays.fill(maxs, max);
        Arrays.fill(bits, bit);

        totalBits = bit * n;
    }

    public int getTotalBits() {
        return totalBits;
    }

    public int getDimensions() {
        return n;
    }

}
