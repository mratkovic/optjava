package hr.fer.zemris.optjava.moop.problems;

public class Problem2 implements MOOPProblem {

    @Override
    public int getNumberOfObjectives() {
        return 2;
    }

    @Override
    public int numberOfVariables() {
        return 2;
    }

    @Override
    public void evaluateSolution(final double[] solution, final double[] objectives) {
        objectives[0] = solution[0];
        objectives[1] = (1 + solution[1]) / solution[0];
    }

    @Override
    public double[] domainLowerBound() {
        return new double[] { 0.1, 0 };
    }

    @Override
    public double[] domainUpperBound() {
        return new double[] { 1, 5 };
    }

    @Override
    public double[] codomainUpperBound() {
        return new double[] { 0.1, 1 };
    }

    @Override
    public double[] codomainLowerBound() {
        return new double[] { 1, 10 };
    }

}
