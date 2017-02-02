package hr.fer.zemris.optjava.moop.problems;

public interface MOOPProblem {

    int getNumberOfObjectives();

    int numberOfVariables();

    double[] domainLowerBound();

    double[] domainUpperBound();

    public double[] codomainUpperBound();

    public double[] codomainLowerBound();

    void evaluateSolution(double[] solution, double[] objectives);
}