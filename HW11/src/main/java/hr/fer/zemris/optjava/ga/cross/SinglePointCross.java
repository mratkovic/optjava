package hr.fer.zemris.optjava.ga.cross;

import hr.fer.zemris.optjava.ga.solution.RectSolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class SinglePointCross<T extends RectSolution> implements ICross<T> {
    IRNG rnd;

    public SinglePointCross() {
    }

    public SinglePointCross(final IRNG rnd) {
        super();
        this.rnd = rnd;
    }

    @Override
    public T crossParents(final T p1, final T p2) {
        if (rnd == null) {
            rnd = RNG.getRNG();
        }

        T child = (T) p1.duplicate();
        int k = rnd.nextInt(0, p1.nRects);
        for (int i = k * 5 + 1; i < child.data.length; ++i) {
            child.data[i] = p2.data[i];
        }
        return child;
    }

}
