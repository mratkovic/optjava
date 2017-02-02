package hr.fer.zemris.optjava.dz2.function;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * Interface that defines function that has 2nd derivatives.
 * 
 * @author marko
 *
 */
public interface IHFunction extends IFunction {

    public RealMatrix getHesseMatrix(RealVector point);
}
