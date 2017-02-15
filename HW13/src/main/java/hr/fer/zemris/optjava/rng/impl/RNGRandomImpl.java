package hr.fer.zemris.optjava.rng.impl;

import java.util.Random;

import hr.fer.zemris.optjava.rng.IRNG;

public class RNGRandomImpl implements IRNG {

    private final Random rnd;

    public RNGRandomImpl() {
        rnd = new Random(System.currentTimeMillis());
    }

    public double nextDouble() {
        return rnd.nextDouble();
    }

    public double nextDouble(final double min, final double max) {
        return min + nextDouble() * (max - min);
    }

    public float nextFloat() {
        return rnd.nextFloat();
    }

    public float nextFloat(final float min, final float max) {
        return min + nextFloat() * (max - min);
    }

    public int nextInt() {
        return rnd.nextInt();
    }

    public int nextInt(final int min, final int max) {
        return min + rnd.nextInt(max - min);
    }

    public boolean nextBoolean() {
        return rnd.nextBoolean();
    }

    public double nextGaussian() {
        return rnd.nextGaussian();
    }

}
