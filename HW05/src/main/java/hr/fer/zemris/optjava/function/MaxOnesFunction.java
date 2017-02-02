package hr.fer.zemris.optjava.function;

import hr.fer.zemris.optjava.ga.solution.BitVectorSolution;

public class MaxOnesFunction implements IFunction<BitVectorSolution> {

    @Override
    public double valueAt(final BitVectorSolution bitVector) {
        double n = bitVector.size();
        double k = bitVector.getNumberOfOnes();

        if (k <= 0.8 * n) {
            return k / n;

        } else if (k > 0.9 * n) {
            return (2.0 * k / n) - 1;

        } else {
            return 0.8;
        }
    }

}
