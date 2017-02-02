package hr.fer.zemris.optjava.ga.cross;

import hr.fer.zemris.optjava.ga.solution.RectSolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class TwoPointCross<T extends RectSolution> implements ICross<T> {
    IRNG rnd;

    public TwoPointCross() {
    }

    public TwoPointCross(final IRNG rnd) {
        super();
        this.rnd = rnd;
    }

    @Override
    public T crossParents(final T p1, final T p2) {
        if (rnd == null) {
            rnd = RNG.getRNG();
        }

        int n = p1.nRects;
        int first = rnd.nextInt(0, n);
        int second = rnd.nextInt(0, n);

        T child = (T) p2.duplicate();
        for (int i = first * 5 + 1; i <= second * 5 + 1; i++) {
            child.data[i] = p1.data[i];
        }
        return child;
    }

}
