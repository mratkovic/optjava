package hr.fer.zemris.trisat;

/**
 * Class that represents mutable variant of {@link BitVector}.
 *
 * @author marko
 *
 */
public class MutableBitVector extends BitVector {
    /**
     * Constructor that creates a BitVector from the given boolean array
     * 
     * @param bits
     *            boolean array
     */
    public MutableBitVector(final boolean... bits) {
        super(bits);
    }

    /**
     * Constructor that creates a BitVector with given length
     *
     * @param n
     *            wanted length of the array
     */
    public MutableBitVector(final int n) {
        super(n);
    }

    /**
     * Setter method that sets bit at given index to given value.
     *
     * @param index
     * @param value
     */
    public void set(final int index, final boolean value) {
        if (index < 0 || index >= bits.length) {
            throw new IndexOutOfBoundsException();
        }
        bits[index] = value;
    }
}