package hr.fer.zemris.optjava.ga.mutation;

import java.util.Random;

import hr.fer.zemris.optjava.ga.solution.DoubleArraySolution;

public class NormMutation implements IMutation<DoubleArraySolution> {
    private double sigma;
    Random rnd;

    public NormMutation(final Random rnd, final double sigma) {
        this.sigma = sigma;
        this.rnd = rnd;
    }

    @Override
    public DoubleArraySolution mutate(final DoubleArraySolution individual) {
        DoubleArraySolution d = individual.duplicate();

        for (int i = 0; i < individual.size(); ++i) {
            d.values[i] += rnd.nextGaussian() * sigma;
        }
        return d;
    }

    @Override
    public void update() {
        sigma /= 2;

    }

}
