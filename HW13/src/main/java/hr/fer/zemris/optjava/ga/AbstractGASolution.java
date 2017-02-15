package hr.fer.zemris.optjava.ga;

public abstract class AbstractGASolution implements Comparable<AbstractGASolution> {
    public double fitness;

    public abstract AbstractGASolution duplicate();

    @Override
    public int compareTo(final AbstractGASolution o) {
        return -Double.compare(this.fitness, o.fitness);
    }

    public abstract void adjustFitness();
}