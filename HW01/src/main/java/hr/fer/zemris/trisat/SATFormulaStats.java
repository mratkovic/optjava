package hr.fer.zemris.trisat;

/**
 * Class that tracks statistics such as number of satisfied clauses and
 * percentage of checked assignments that satisfied each clause for given
 * {@link SATFormula}.
 *
 * @author marko
 *
 */
public class SATFormulaStats {

    private final SATFormula formula;
    private boolean isSatisfied;
    private int nSatisfied;
    private double percentageBonus;
    private final double[] post;

    private final double percentageConstantUp = 0.01;
    private final double percentageConstantDown = 0.1;
    private final int percentageUnitAmount = 50;

    /**
     * Constructor for SATFormulaStats that initializes the SATFormula
     *
     * @param formula
     *            whose statistics will be tracked
     */
    public SATFormulaStats(final SATFormula formula) {
        this.formula = formula;
        post = new double[formula.getNumberOfClauses()];
    }

    /**
     * Sets the assessment. If the boolean parameter is true percentages are
     * updated.
     *
     * @param assignment
     *            t
     * @param updatePercentages
     *            should percentages be updated
     */
    public void setAssignment(final BitVector assignment, final boolean updatePercentages) {
        isSatisfied = formula.isSatisfied(assignment);
        nSatisfied = 0;
        percentageBonus = 0;

        for (int i = 0; i < formula.getNumberOfClauses(); ++i) {
            Clause c = formula.getClause(i);
            double updatePost = 0;

            if (c.isSatisfied(assignment)) {
                updatePost = (1 - post[i]) * percentageConstantUp;
                nSatisfied++;
                percentageBonus += percentageUnitAmount * (1 - post[i]);

            } else {
                updatePost = (0 - post[i]) * percentageConstantDown;
                percentageBonus -= percentageUnitAmount * (1 - post[i]);
            }

            if (updatePercentages) {
                post[i] += updatePost;
            }
        }
    }

    /**
     * Gets number of satisfied clauses by the current assignment
     *
     * @return number of satisfied clauses
     */
    public int getNumberOfSatisfied() {
        return nSatisfied;
    }

    /**
     * Method checks if the formula is satisfied by the current assignment
     *
     * @return true if the formula is satisfied
     */
    public boolean isSatisfied() {
        return isSatisfied;
    }

    /**
     * Gets percentage bonus of all clauses.
     *
     * @return percentage bonus
     */
    public double getPercentageBonus() {
        return percentageBonus;
    }

    /**
     * Returns the percentage of assignments that satisfied clause at given
     * index.
     *
     * @param index
     * @return percentage
     */
    public double getPercentage(final int index) {
        if (index < 0 || index >= post.length) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return post[index];
    }
}