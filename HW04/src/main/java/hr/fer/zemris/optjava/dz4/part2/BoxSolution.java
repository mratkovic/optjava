package hr.fer.zemris.optjava.dz4.part2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import hr.fer.zemris.optjava.ga.solution.SingleObjectiveSolution;

public class BoxSolution extends SingleObjectiveSolution {
    public int[] values;

    public BoxSolution(final int n) {
        values = new int[n];
        for (int i = 0; i < n; ++i) {
            values[i] = i;
        }
    }

    public BoxSolution newLikeThis() {
        return new BoxSolution(values.length);
    }

    @Override
    public BoxSolution duplicate() {
        BoxSolution d = new BoxSolution(values.length);
        d.values = Arrays.copyOf(values, values.length);
        return d;
    }

    public void randomize(final Random rnd) {
        List<Integer> l = Arrays.stream(values).boxed().collect(Collectors.toList());
        Collections.shuffle(l);
        values = l.stream().mapToInt(i -> i).toArray();
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
        BoxSolution other = (BoxSolution) obj;
        if (!Arrays.equals(values, other.values)) {
            return false;
        }
        return true;
    }

}
