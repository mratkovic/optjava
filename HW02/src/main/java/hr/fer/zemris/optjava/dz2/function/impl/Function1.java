package hr.fer.zemris.optjava.dz2.function.impl;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import hr.fer.zemris.optjava.dz2.function.IHFunction;

/**
 * Function used in first task. f(x) = x1^2 + (x2 - 1) ^2
 *
 * @author marko
 *
 */
public class Function1 implements IHFunction {

    @Override
    public int numberOfVariables() {
        return 2;
    }

    @Override
    public RealVector getValue(final RealVector point) {
        checkDimensions(point);
        double x1 = point.getEntry(0);
        double x2 = point.getEntry(1);

        double val = x1 * x1 + (x2 - 1) * (x2 - 1);
        return MatrixUtils.createRealVector(new double[] { val });
    }

    @Override
    public RealVector getGradient(final RealVector point) {
        checkDimensions(point);
        double x1 = point.getEntry(0);
        double x2 = point.getEntry(1);

        double dx1 = 2 * x1;
        double dx2 = 2 * (x2 - 1);
        return MatrixUtils.createRealVector(new double[] { dx1, dx2 });
    }

    @Override
    public RealMatrix getHesseMatrix(final RealVector point) {
        checkDimensions(point);
        return MatrixUtils.createRealMatrix(new double[][] { { 2, 0 }, { 0, 2 } });
    }

    private void checkDimensions(final RealVector pt) {
        if (pt.getDimension() != 2) {
            throw new IllegalArgumentException("Expected vector of dimension 2");
        }

    }
}
