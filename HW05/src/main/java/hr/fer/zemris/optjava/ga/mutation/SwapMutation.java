package hr.fer.zemris.optjava.ga.mutation;

import java.util.Random;

import hr.fer.zemris.optjava.ga.solution.Permutation;

public class SwapMutation implements IMutation<Permutation> {
    Random rnd;

    public SwapMutation(final Random rnd) {
        super();
        this.rnd = rnd;
    }

    @Override
    public Permutation mutate(final Permutation individual) {
        Permutation d = individual.duplicate();

        int first = rnd.nextInt(individual.values.length);
        int second = rnd.nextInt(individual.values.length);

        int tmp = d.values[first];
        d.values[first] = d.values[second];
        d.values[second] = tmp;

        return d;

    }

}
