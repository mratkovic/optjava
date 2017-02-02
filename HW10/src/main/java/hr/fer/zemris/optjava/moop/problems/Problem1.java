package hr.fer.zemris.optjava.moop.problems;

public class Problem1 implements MOOPProblem {

    public Problem1() {
    }

    @Override
    public int numberOfVariables() {
        return 4;
    }

    @Override
    public double[] domainUpperBound() {
        return new double[] { 5, 5, 5, 5 };
    }

    @Override
    public double[] domainLowerBound() {
        return new double[] { -5, -5, -5, -5 };
    }

    @Override
    public double[] codomainUpperBound() {
        return new double[] { 25, 25, 25, 25 };
    }

    @Override
    public double[] codomainLowerBound() {
        return new double[] { 0, 0, 0, 0 };
    }

    @Override
    public int getNumberOfObjectives() {
        return 4;
    }

    @Override
    public void evaluateSolution(final double[] solution, final double[] objectives) {
        for (int i = 0; i < numberOfVariables(); i++) {
            objectives[i] = Math.pow(solution[i], 2);
        }
    }

}
