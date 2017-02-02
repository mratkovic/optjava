package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Class that models {@link SATFormula} given in cnf form..
 *
 * @author marko
 *
 */
public class SATFormula {
    /** Number of variables in formula */
    private final int numberOfVariables;
    /** Array of clauses. */
    private final Clause[] clauses;

    /**
     * Constructor that instances formula.
     *
     * @param numberOfVariables
     * @param clauses
     */
    public SATFormula(final int numberOfVariables, final Clause[] clauses) {
        if (numberOfVariables <= 0) {
            throw new IllegalArgumentException("Number of variables must be positive integer value");
        }

        if (clauses == null) {
            throw new IllegalArgumentException("Null value passed as clauses array");
        }

        this.numberOfVariables = numberOfVariables;
        this.clauses = Arrays.copyOf(clauses, clauses.length);
    }

    /**
     * Getter method for number of variables.
     *
     * @return number of variables
     */
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    /**
     * Getter method for number of clauses.
     *
     * @return number of clause
     */
    public int getNumberOfClauses() {
        return clauses.length;
    }

    /**
     * Method that gets clause at given index.
     *
     * @param index
     * @return clause
     */
    public Clause getClause(final int index) {
        if (index < 0 || index >= clauses.length) {
            throw new IllegalArgumentException("Invalid index");
        }
        return clauses[index];
    }

    /**
     * Method that checks if given assignment satisfies all clauses.
     *
     * @param assignment
     * @return true if all clauses are satisfied
     */
    public boolean isSatisfied(final BitVector assignment) {
        for (Clause c : clauses) {
            if (!c.isSatisfied(assignment)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return Arrays.stream(clauses).map(c -> c.toString()).collect(Collectors.joining(""));
    }
}