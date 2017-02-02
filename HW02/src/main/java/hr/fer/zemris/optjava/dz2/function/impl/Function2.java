package hr.fer.zemris.optjava.dz2.function.impl;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import hr.fer.zemris.optjava.dz2.function.IHFunction;

/**
 * Function used in first task. f(x) = (x1-1)^2 + 10(x2-2)^2
 *
 * @author marko
 *
 */
public class Function2 implements IHFunction {

    @Override
    public int numberOfVariables() {
        return 2;
    }

    @Override
    public RealVector getValue(final RealVector point) {
        checkDimensions(point);
        double x1 = point.getEntry(0);
        double x2 = point.getEntry(1);

        double val = Math.pow(x1 - 1, 2) + 10 * Math.pow(x2 - 2, 2);
        return MatrixUtils.createRealVector(new double[] { val });
    }

    @Override
    public RealVector getGradient(final RealVector point) {
        checkDimensions(point);
        double x1 = point.getEntry(0);
        double x2 = point.getEntry(1);

        double dx1 = 2 * (x1 - 1);
        double dx2 = 20 * (x2 - 2);
        return MatrixUtils.createRealVector(new double[] { dx1, dx2 });
    }

    @Override
    public RealMatrix getHesseMatrix(final RealVector point) {
        checkDimensions(point);
        return MatrixUtils.createRealMatrix(new double[][] { { 2, 0 }, { 0, 20 } });
    }

    private void checkDimensions(final RealVector pt) {
        if (pt.getDimension() != 2) {
            throw new IllegalArgumentException("Expected vector of dimension 2");
        }

    }

}
