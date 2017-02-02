package hr.fer.zemris.optjava.moop.cross;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.moop.solution.DoubleArraySolution;

public class BLXCross implements ICross<DoubleArraySolution> {

    private final double alpha;
    private final Random rnd;

    public BLXCross(final double alpha, final Random rnd) {
        super();
        this.alpha = alpha;
        this.rnd = rnd;
    }

    @Override
    public List<DoubleArraySolution> crossParents(final DoubleArraySolution p1, final DoubleArraySolution p2) {
        DoubleArraySolution child = p1.newLikeThis();

        for (int i = 0; i < p1.size(); ++i) {
            double min = Double.min(p1.values[i], p2.values[i]);
            double max = Double.max(p1.values[i], p2.values[i]);
            double interval = max - min;

            min -= alpha * interval;
            max += alpha * interval;
            interval = max - min;
            child.values[i] = rnd.nextDouble() * interval + min;

        }

        List<DoubleArraySolution> ret = new ArrayList<>();
        ret.add(child);
        return ret;
    }

}
