package hr.fer.zemris.trisat.algorithm;

import hr.fer.zemris.trisat.SATFormula;

/**
 * Interface that defines single method that solves given {@link SATFormula}.
 *
 * @author marko
 *
 */
public interface IAlgorithm {
    /**
     * Method that solves given {@link SATFormula}. Solution and other
     * informations about algorithm are printed to standard out.
     *
     * @param satFormula
     */
    public void solve(SATFormula satFormula);
}
