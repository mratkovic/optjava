package hr.fer.zemris.optjava.dz4.part2;

import java.util.Random;

import hr.fer.zemris.optjava.ga.mutation.IMutation;

public class SwapStickMutation implements IMutation<BoxSolution> {
    Random rnd;

    public SwapStickMutation(final Random rnd) {
        super();
        this.rnd = rnd;
    }

    @Override
    public BoxSolution mutate(final BoxSolution individual) {
        BoxSolution d = individual.duplicate();

        int first = rnd.nextInt(individual.values.length);
        int second = rnd.nextInt(individual.values.length);

        int tmp = d.values[first];
        d.values[first] = d.values[second];
        d.values[second] = tmp;

        return d;
    }

    @Override
    public void update() {
    }

}
