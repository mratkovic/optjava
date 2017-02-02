package hr.fer.zemris.optimizations.ann;

public interface IANN {
    public int getWeightsCount();

    public double[] calcOutputs(double[] inputs);

    public double getMSE();

    public void setWeights(final double[] weights);

}
