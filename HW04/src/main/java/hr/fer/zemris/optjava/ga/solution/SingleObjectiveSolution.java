package hr.fer.zemris.optjava.ga.solution;

public abstract class SingleObjectiveSolution implements Comparable<SingleObjectiveSolution> {

    public double fitness;
    public double value;

    public SingleObjectiveSolution() {

    }

    @Override
    public int compareTo(final SingleObjectiveSolution solution) {
        return Double.compare(fitness, solution.fitness);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(fitness);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SingleObjectiveSolution other = (SingleObjectiveSolution) obj;
        if (Double.doubleToLongBits(fitness) != Double.doubleToLongBits(other.fitness)) {
            return false;
        }
        if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value)) {
            return false;
        }
        return true;
    }

    public abstract SingleObjectiveSolution duplicate();

}
