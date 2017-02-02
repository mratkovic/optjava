package hr.fer.zemris.optjava.ga.decoder;

public interface IDecoder<T> {

    public double[] decode(T value);

    public void decode(T value, double[] values);

    public void printSolution(T best);

}