package hr.fer.zemris.optjava.dz3.solution;

public class SingleObjectiveSolution implements Comparable<SingleObjectiveSolution> {

    public double fitness;
    public double value;

    @Override
    public int compareTo(final SingleObjectiveSolution solution) {
        return Double.compare(fitness, solution.fitness);
    }

}
