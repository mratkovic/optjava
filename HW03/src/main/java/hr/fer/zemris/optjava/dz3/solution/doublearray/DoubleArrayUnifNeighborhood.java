package hr.fer.zemris.optjava.dz3.solution.doublearray;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz3.solution.INeighborhood;

public class DoubleArrayUnifNeighborhood implements INeighborhood<DoubleArraySolution> {

    private final double[] deltas;
    Random rand;

    public DoubleArrayUnifNeighborhood(final double[] deltas) {
        this.deltas = deltas;
        this.rand = new Random();
    }

    public DoubleArrayUnifNeighborhood(final int n, final double delta) {
        deltas = new double[n];
        Arrays.fill(deltas, delta);
        this.rand = new Random();
    }

    @Override
    public DoubleArraySolution randomNeighbor(final DoubleArraySolution n) {
        DoubleArraySolution d = n.duplicate();

        int i = rand.nextInt(deltas.length);
        // for (int i = 0; i < d.values.length; i++) {
        d.values[i] += 2 * rand.nextDouble() * deltas[i] - deltas[i];
        // }
        return d;
    }

}
