package hr.fer.zemris.optjava.dz3.solution.bit;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.solution.INeighborhood;

public class BitvectorSingleBitNeighborhood implements INeighborhood<BitvectorSolution> {

    Random rand;

    public BitvectorSingleBitNeighborhood() {
        rand = new Random();
    }

    @Override
    public BitvectorSolution randomNeighbor(final BitvectorSolution n) {
        BitvectorSolution rv = n.duplicate();

        int index = rand.nextInt(rv.bits.length);
        rv.bits[index] ^= 1;
        return rv;
    }

}
