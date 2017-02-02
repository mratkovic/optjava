package hr.fer.zemris.optjava.ga.solution;

public abstract class GASolution<T> implements Comparable<GASolution<T>> {
    public T data;
    public double fitness;

    public GASolution() {
    }

    public T getData() {
        return data;
    }

    public abstract GASolution<T> duplicate();

    @Override
    public int compareTo(final GASolution<T> o) {
        return -Double.compare(this.fitness, o.fitness);
    }
}