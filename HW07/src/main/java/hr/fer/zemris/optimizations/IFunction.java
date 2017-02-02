package hr.fer.zemris.optimizations;

public interface IFunction {

    public double valueAt(double[] sol);

    public int nVariables();

}
