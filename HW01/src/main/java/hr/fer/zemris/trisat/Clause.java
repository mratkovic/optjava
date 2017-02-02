package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class that represents single clause in CNF formula.
 *
 * @author marko
 *
 */
public class Clause {
    /** Unicode symbol for negation */
    private static final String COMPLEMENT_SYM = "\u00AC";

    private final int[] indexes;

    public Clause(final int[] indexes) {
        if (indexes == null) {
            throw new IllegalArgumentException("Null value passed");
        }

        this.indexes = Arrays.copyOf(indexes, indexes.length);
    }

    /**
     * Number of literals in clause.
     *
     * @return size
     */
    public int getSize() {
        return indexes.length;
    }

    /**
     * Get literal at given index
     *
     * @param index
     * @return literal
     */
    public int getLiteral(final int index) {
        if (index < 0 || index >= indexes.length) {
            throw new IllegalArgumentException("Invalid index");
        }
        return indexes[index];
    }

    /**
     * Method that checks if given assignment satisfies clause.
     *
     * @param assignment
     * @return isSatisfied
     */
    public boolean isSatisfied(final BitVector assignment) {
        for (int index : indexes) {
            int id = Math.abs(index) - 1;
            boolean needed = index > 0;
            boolean satisfied = assignment.get(id) == needed;

            if (satisfied) {
                return true;
            }

        }
        return false;
    }

    @Override
    public String toString() {
        Stream<String> strStream = Arrays.stream(indexes).mapToObj(i -> indexToVariableString(i));
        return strStream.collect(Collectors.joining(" + ", "(", ")"));
    }

    /**
     * Util method to convert literal index to string variable representation
     * "Xy" where y is index.
     *
     * @param i
     *            position of literal
     * @return string representation
     */
    private static String indexToVariableString(final int i) {
        if (i < 0) {
            return COMPLEMENT_SYM + "X" + (i * -1);
        } else {
            return "X" + i;
        }

    }
}