package hr.fer.zemris.optjava.ga.solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Permutation extends SingleObjectiveSolution {
    public int[] values;

    public Permutation(final int n) {
        values = new int[n];
        for (int i = 0; i < n; ++i) {
            values[i] = i;
        }
    }

    public Permutation newLikeThis() {
        return new Permutation(values.length);
    }

    @Override
    public Permutation duplicate() {
        Permutation d = new Permutation(values.length);
        d.values = Arrays.copyOf(values, values.length);
        return d;
    }

    public void randomize(final Random rnd) {
        List<Integer> l = Arrays.stream(values).boxed().collect(Collectors.toList());
        Collections.shuffle(l);
        values = l.stream().mapToInt(i -> i).toArray();
    }

    public int[] getValues() {
        return values;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(values);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Permutation other = (Permutation) obj;
        if (!Arrays.equals(values, other.values)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int b : values) {
            sb.append(b);
            sb.append(" ");
        }
        return "Permutation: " + sb.toString();
    }

}
