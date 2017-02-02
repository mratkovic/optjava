package hr.fer.zemris.optjava.dz3.solution.bit;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

public class BitvectorSolution extends SingleObjectiveSolution {

    public byte[] bits;

    public BitvectorSolution(final int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Invalid size argument");
        }
        bits = new byte[size];
    }

    public BitvectorSolution(final byte[] bits) {
        super();
        this.bits = bits;
    }

    public BitvectorSolution newLikeThis() {
        return new BitvectorSolution(bits.length);
    }

    public BitvectorSolution duplicate() {
        BitvectorSolution bv = this.newLikeThis();
        bv.bits = Arrays.copyOf(bits, bits.length);
        return bv;
    }

    public void randomize(final Random rnd) {
        for (int i = 0; i < bits.length; ++i) {
            bits[i] = (byte) (rnd.nextBoolean() ? 1 : 0);
        }
    }

}
