package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import hr.fer.zemris.optjava.ga.cross.ICross;

public class OX2Cross implements ICross<BoxSolution> {
    Random rnd;

    public OX2Cross(final Random rnd) {
        super();
        this.rnd = rnd;
    }

    @Override
    public List<BoxSolution> crossParents(final BoxSolution p1, final BoxSolution p2) {
        BoxSolution sol = krizaj(p1, p2, rnd);
        List<BoxSolution> l = new ArrayList<>();
        l.add(sol);
        return l;
    }

    private static BoxSolution krizaj(final BoxSolution p1, final BoxSolution p2, final Random rnd) {
        int n = p1.values.length;
        BoxSolution sol = new BoxSolution(n);
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

        // copy first
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
