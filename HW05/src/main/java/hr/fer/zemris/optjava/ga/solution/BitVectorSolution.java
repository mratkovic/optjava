package hr.fer.zemris.optjava.ga.solution;

import java.util.Arrays;
import java.util.Random;

public class BitVectorSolution extends SingleObjectiveSolution {

    public byte[] bits;
    private int numberOfOnes;

    public BitVectorSolution(final int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Invalid size argument");
        }
        bits = new byte[size];
        numberOfOnes = 0;
    }

    public BitVectorSolution(final int size, final Random rnd) {
        this(size);
        randomize(rnd);
    }

    public BitVectorSolution(final byte[] bits) {
        super();
        this.bits = bits;
        updateNumberOfOnes();
    }

    public BitVectorSolution newLikeThis() {
        return new BitVectorSolution(bits.length);
    }

    @Override
    public BitVectorSolution duplicate() {
        BitVectorSolution bv = this.newLikeThis();
        bv.bits = Arrays.copyOf(bits, bits.length);
        bv.numberOfOnes = numberOfOnes;
        return bv;
    }

    public void randomize(final Random rnd) {
        for (int i = 0; i < bits.length; ++i) {
            bits[i] = (byte) (rnd.nextBoolean() ? 1 : 0);
        }
        updateNumberOfOnes();
    }

    private void updateNumberOfOnes() {
        numberOfOnes = 0;
        for (byte bit : bits) {
            numberOfOnes += bit;
        }
    }

    public int getNumberOfOnes() {
        updateNumberOfOnes();
        return numberOfOnes;
    }

    public int size() {
        return bits.length;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(bits);
        result = prime * result + numberOfOnes;
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
        BitVectorSolution other = (BitVectorSolution) obj;
        if (!Arrays.equals(bits, other.bits)) {
            return false;
        }
        if (numberOfOnes != other.numberOfOnes) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte b : bits) {
            sb.append(b);
        }
        return "BitVector: " + sb.toString();
    }

}
