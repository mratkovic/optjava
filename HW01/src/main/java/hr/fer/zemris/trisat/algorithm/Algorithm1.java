package hr.fer.zemris.trisat.algorithm;

import java.math.BigInteger;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.SATFormula;

/**
 * Class that implement simple algorithm that tests all possible combinations of
 * variables and finds all solutions that satisfy given formula.
 *
 * @author marko
 *
 */
public class Algorithm1 implements IAlgorithm {
    // BigInteger used so formulas with number of variables larger than 64
    // can be solved with this simple approach. Instead of BigInteger and
    // adding one, generation of all bit masks could be implemented
    @Override
    public void solve(final SATFormula satFormula) {
        BitVector solution = null;
        int nVars = satFormula.getNumberOfVariables();

        BigInteger current = new BigInteger("0");
        BigInteger max = new BigInteger("2").pow(nVars);

        while (current.compareTo(max) < 0) {
            BitVector bVector = new BitVector(current.toString(2), nVars);
            if (satFormula.isSatisfied(bVector)) {
                solution = bVector;
                System.out.println(solution);
            }
            current = current.add(BigInteger.ONE);
        }
    }

}
