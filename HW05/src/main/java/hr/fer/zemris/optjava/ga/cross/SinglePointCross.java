package hr.fer.zemris.optjava.ga.cross;

import java.util.Random;

import hr.fer.zemris.optjava.ga.solution.BitVectorSolution;

public class SinglePointCross implements ICross<BitVectorSolution> {
    public Random rnd;

    public SinglePointCross(final Random rnd) {
        super();
        this.rnd = rnd;
    }

    @Override
    public BitVectorSolution crossParents(final BitVectorSolution p1, final BitVectorSolution p2) {
        int n = p1.size();
        BitVectorSolution child = p2.duplicate();

        int k = rnd.nextInt(n);

        for (int i = 0; i < n; i++) {
            if (i < k) {
                child.bits[i] = p1.bits[i];
            } else {
                child.bits[i] = p2.bits[i];
            }
        }
        return child;
    }

}
