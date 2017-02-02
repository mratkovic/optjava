package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class that generates neighborhood for given bit vector. Neighborhood is
 * defined as all those bit vectors whose Hamming distance from initial vector
 * is equal one.
 *
 * @author marko
 *
 */
public class BitVectorNGenerator implements Iterable<MutableBitVector> {
    /** Initial vector */
    private final BitVector assignment;

    /**
     * Constructor
     *
     * @param assignment
     *            initial assigment
     */
    public BitVectorNGenerator(final BitVector assignment) {
        this.assignment = assignment;
    }

    @Override
    public Iterator<MutableBitVector> iterator() {
        return new Iterator<MutableBitVector>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < assignment.getSize();
            }

            @Override
            public MutableBitVector next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                MutableBitVector n = assignment.copy();
                n.set(currentIndex, !assignment.get(currentIndex));
                currentIndex++;
                return n;

            }

        };
    }

    /**
     * Method that creates neighborhood.
     *
     * @return array of mutable vectors that represent neighbors of initial
     *         vector
     */
    public MutableBitVector[] createNeighborhood() {
        List<MutableBitVector> neighborhood = new ArrayList<>();
        iterator().forEachRemaining(vec -> neighborhood.add(vec));

        MutableBitVector[] arr = new MutableBitVector[neighborhood.size()];
        return neighborhood.toArray(arr);
    }
}