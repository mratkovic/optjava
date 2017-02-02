package hr.fer.zemris.optjava.ga.cross;

import java.util.Random;

import hr.fer.zemris.optjava.ga.solution.BitVectorSolution;

public class TwoPointCross implements ICross<BitVectorSolution> {
    public Random rnd;

    public TwoPointCross(final Random rnd) {
        super();
        this.rnd = rnd;
    }

    @Override
    public BitVectorSolution crossParents(final BitVectorSolution p1, final BitVectorSolution p2) {
        int n = p1.size();
        BitVectorSolution child = p2.duplicate();

        int first = rnd.nextInt(n);
        int second = rnd.nextInt(n);

        for (int i = first; i <= second; i++) {
            child.bits[i] = p1.bits[i];
        }
        return child;
    }

}
