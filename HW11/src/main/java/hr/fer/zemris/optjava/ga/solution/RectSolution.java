package hr.fer.zemris.optjava.ga.solution;

import java.util.Arrays;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class RectSolution extends GASolution<int[]> {

    public int nRects;
    IRNG rnd;

    public RectSolution(final int length) {
        this.data = new int[length];
        nRects = (length - 1) / 5;
    }

    public RectSolution(final int[] data, final double fitness) {
        this.data = data;
        this.fitness = fitness;
        nRects = (data.length - 1) / 5;
    }

    @Override
    public GASolution<int[]> duplicate() {
        return new RectSolution(Arrays.copyOf(data, data.length), fitness);
    }

    public void randomize(final int pictureWidth, final int pictureHeight) {
        if (rnd == null) {
            rnd = RNG.getRNG();
        }

        data[0] = rnd.nextInt(0, 256); // background

        for (int i = 1; i < data.length;) {
            int x = rnd.nextInt(0, pictureWidth);
            int y = rnd.nextInt(0, pictureHeight);
            int w = rnd.nextInt(x, pictureWidth);
            int h = rnd.nextInt(y, pictureHeight);

            data[i++] = x;
            data[i++] = y;
            data[i++] = w;
            data[i++] = h;
            // clr
            data[i++] = rnd.nextInt(0, 256);

        }
    }
}
