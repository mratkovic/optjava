package hr.fer.zemris.trisat.test;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.Parser;
import hr.fer.zemris.trisat.SATFormula;

/**
 * Dummy class used to test various things while implementing them. No unit
 * tests here :D
 * 
 * @author marko
 *
 */
public class Test {

    public static void main(final String[] args) {
        SATFormula f = Parser.parseFile("./cnfs/uf20-01.cnf");

        System.out.println(f.toString());

        BitVector bv = new BitVector("0000");
        System.out.println(bv);
        BitVectorNGenerator ng = new BitVectorNGenerator(bv);
        MutableBitVector[] all = ng.createNeighborhood();

        for (BitVector v : all) {
            System.out.println(v);
        }
        System.out.println();
        System.out.println();

        for (BitVector v : ng) {
            System.out.println(v);
        }
    }
}
