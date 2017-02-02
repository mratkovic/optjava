package hr.fer.zemris.optjava.moop.solution;

import java.util.Arrays;

public abstract class MultiObjectiveSolution {

    public double[] objective;
    public double fitness;
    public double distance;
    public int rank;

    public MultiObjectiveSolution(final int n) {
        objective = new double[n];
    }

    public abstract MultiObjectiveSolution duplicate();

    @Override
    public String toString() {
        return Arrays.toString(objective);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(distance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fitness);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + Arrays.hashCode(objective);
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
        MultiObjectiveSolution other = (MultiObjectiveSolution) obj;
        if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance)) {
            return false;
        }
        if (Double.doubleToLongBits(fitness) != Double.doubleToLongBits(other.fitness)) {
            return false;
        }
        if (!Arrays.equals(objective, other.objective)) {
            return false;
        }
        return true;
    }

}
