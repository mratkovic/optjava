package hr.fer.zemris.optjava.rng;

public interface IRNG {
    public double nextDouble();

    public double nextDouble(double min, double max);

    public float nextFloat();

    public float nextFloat(float min, float max);

    public int nextInt();

    public int nextInt(int min, int max);

    public boolean nextBoolean();

    public double nextGaussian();
}