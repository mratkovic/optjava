package hr.fer.zemris.trisat.algorithm;

import hr.fer.zemris.trisat.BitVector;

public class SolutonCandidate implements Comparable<SolutonCandidate> {
    private BitVector bVector;
    private double fitness;

    public SolutonCandidate(final BitVector bVector, final double fitness) {
        super();
        this.bVector = bVector;
        this.fitness = fitness;
    }

    public BitVector getBitVector() {
        return bVector;
    }

    public void setBitVector(final BitVector bVector) {
        this.bVector = bVector;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(final double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(final SolutonCandidate o) {
        return Double.compare(fitness, o.fitness);
    }

}
