package hr.fer.zemris.optjava.moop.problems;

public interface MOOPProblem {

    int getNumberOfObjectives();

    int numberOfVariables();

    double[] domainLowerBound();

    double[] domainUpperBound();

    void evaluateSolution(double[] solution, double[] objectives);
}