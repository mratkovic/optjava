package hr.fer.zemris.optjava.dz2.function;

import org.apache.commons.math3.linear.RealVector;

/**
 * Interface that defines derivable function.
 *
 * @author marko
 *
 */
public interface IFunction {

    public int numberOfVariables();

    public RealVector getValue(RealVector point);

    public RealVector getGradient(RealVector point);

}
