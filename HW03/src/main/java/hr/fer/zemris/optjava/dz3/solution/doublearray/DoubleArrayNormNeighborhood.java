package hr.fer.zemris.optjava.dz3.solution.doublearray;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.solution.INeighborhood;

public class DoubleArrayNormNeighborhood implements INeighborhood<DoubleArraySolution> {

    private final double[] deltas;
    Random rand;

    public DoubleArrayNormNeighborhood(final double[] deltas) {
        this.deltas = deltas;
        rand = new Random();
    }

    @Override
    public DoubleArraySolution randomNeighbor(final DoubleArraySolution n) {
        DoubleArraySolution d = n.duplicate();

        for (int i = 0; i < d.values.length; i++) {
            d.values[i] += rand.nextGaussian() * deltas[i];
        }
        return d;
    }

}
