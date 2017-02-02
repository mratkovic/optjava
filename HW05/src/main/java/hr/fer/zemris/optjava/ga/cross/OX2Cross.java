package hr.fer.zemris.optjava.ga.cross;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import hr.fer.zemris.optjava.ga.solution.Permutation;

public class OX2Cross implements ICross<Permutation> {
    Random rnd;

    public OX2Cross(final Random rnd) {
        super();
        this.rnd = rnd;
    }

    @Override
    public Permutation crossParents(final Permutation p1, final Permutation p2) {
        int n = p1.values.length;
        Permutation sol = new Permutation(n);
        Queue<Integer> a = new LinkedList<>();
        Queue<Integer> b = new LinkedList<>();

        for (int i = 0; i < n; ++i) {
            a.add(p1.values[i]);
            b.add(p2.values[i]);
        }

        int i1 = rnd.nextInt(n);
        int i2 = rnd.nextInt(n);
        i1 = Math.min(i1, i2);
        i2 = Math.max(i1, i2);

        // mid
        for (int i = i1; i <= i2; i++) {
            Integer tmp = a.poll();
            b.remove(tmp);
            sol.values[i] = tmp;
        }

        // last
        for (int i = i2 + 1; i < n; i++) {
            Integer tmp = b.poll();
            sol.values[i] = tmp;
        }
        // first
        for (int i = 0; i < i1; i++) {
            Integer tmp = b.poll();
            sol.values[i] = tmp;
        }

        return sol;
    }

}
