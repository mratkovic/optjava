package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Random;

/**
 * Class that represents bit vector, structure consisting of boolean array.
 *
 * @author marko
 *
 */
public class BitVector {
    /** Internal representation of bit vector */
    protected boolean[] bits;

    /**
     * Constructor that randomly initializes bit vector of given lengt.
     *
     * @param rand
     *            random generator
     * @param numberOfBits
     *            number of bits in bit vector
     */
    public BitVector(final Random rand, final int numberOfBits) {
        if (numberOfBits < 0) {
            throw new IllegalArgumentException("Number of bits must be positive value");
        }

        bits = new boolean[numberOfBits];
        for (int i = 0; i < numberOfBits; ++i) {
            bits[i] = rand.nextBoolean();
        }
    }

    /**
     * Constructor
     *
     * @param bits
     *            bits of bit vector
     */
    public BitVector(final boolean... bits) {
        if (bits == null) {
            throw new IllegalArgumentException("Null value passed");
        }
        this.bits = Arrays.copyOf(bits, bits.length);
    }

    /**
     * Constructor that initializes bit vector of length n filled with zeros.
     *
     * @param n
     */
    public BitVector(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number of bits must be positive value");
        }
        this.bits = new boolean[n];
    }

    /**
     * Constructor that initializes bit vector of length n with initials values
     * taken from mask.
     *
     * @param mask
     *            init mask
     */
    public BitVector(final String mask, final int nVariables) {
        this.bits = new boolean[nVariables];

        int padding = nVariables - mask.length();
        for (int i = 0; i < padding; ++i) {
            this.bits[i] = false;
        }

        for (int i = 0; i < mask.length(); ++i) {
            char c = mask.charAt(i);
            if (c != '0' && c != '1') {
                throw new IllegalArgumentException("Expected string consisting of symbols '0' and '1'");
            }
            this.bits[padding + i] = c == '1';

        }

    }

    /**
     * Constructor that initializes bit vector with passed bit mask (String
     *
     * filled with 0 and 1).
     *
     * @param mask
     *            init mask
     */
    public BitVector(final String mask) {
        this(mask, mask.length());
    }

    /**
     * Get value of bit vector at given index
     *
     * @param index
     * @return bit (true or false)
     */
    public boolean get(final int index) {
        if (index < 0 || index >= bits.length) {
            throw new IllegalArgumentException("Invalid index");
        }
        return bits[index];
    }

    /**
     * Method that returns size of given vector
     *
     * @return size
     */
    public int getSize() {
        return bits.length;
    }

    @Override
    public String toString() {
        // fuck logic, not implemented: boolean[] -> stream
        // Arrays.stream(bits).mapToObj(b -> b ? "1"
        // :"0").collect(Collectors.joining(""));

        StringBuilder sb = new StringBuilder();
        for (boolean bit : bits) {
            sb.append(bit ? "1" : "0");
        }
        return sb.toString();
    }

    /**
     * Method that return mutable copy of bit vector.
     *
     * @return mutable copy
     */
    public MutableBitVector copy() {
        return new MutableBitVector(bits);
    }

}
