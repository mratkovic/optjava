package hr.fer.zemris.optjava.ga.mutation;

import java.util.Random;

import hr.fer.zemris.optjava.ga.solution.BitVectorSolution;

public class ToggleBitMutation implements IMutation<BitVectorSolution> {

    public Random rnd;

    public ToggleBitMutation(final Random rnd) {
        super();
        this.rnd = rnd;
    }

    @Override
    public BitVectorSolution mutate(final BitVectorSolution n) {
        BitVectorSolution rv = n.duplicate();

        int index = rnd.nextInt(rv.size());
        rv.bits[index] ^= 1;
        return rv;
    }

}
