package hr.fer.zemris.optjava.dz3.solution;

public interface IDecoder<T> {

    public double[] decode(T value);

    public void decode(T value, double[] values);

}