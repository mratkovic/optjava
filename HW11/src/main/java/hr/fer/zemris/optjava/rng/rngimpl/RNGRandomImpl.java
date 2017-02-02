package hr.fer.zemris.optjava.rng.rngimpl;

import java.util.Random;

import hr.fer.zemris.optjava.rng.IRNG;

public class RNGRandomImpl implements IRNG {

    private final Random rnd;

    public RNGRandomImpl() {
        rnd = new Random(System.currentTimeMillis());
    }

    @Override
    public double nextDouble() {
        return rnd.nextDouble();
    }

    @Override
    public double nextDouble(final double min, final double max) {
        return min + nextDouble() * (max - min);
    }

    @Override
    public float nextFloat() {
        return rnd.nextFloat();
    }

    @Override
    public float nextFloat(final float min, final float max) {
        return min + nextFloat() * (max - min);
    }

    @Override
    public int nextInt() {
        return rnd.nextInt();
    }

    @Override
    public int nextInt(final int min, final int max) {
        return min + rnd.nextInt(max - min);
    }

    @Override
    public boolean nextBoolean() {
        return rnd.nextBoolean();
    }

    @Override
    public double nextGaussian() {
        return rnd.nextGaussian();
    }

}
