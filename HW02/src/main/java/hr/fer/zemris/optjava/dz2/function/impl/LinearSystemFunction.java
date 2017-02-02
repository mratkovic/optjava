package hr.fer.zemris.optjava.dz2.function.impl;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import hr.fer.zemris.optjava.dz2.function.IHFunction;
import hr.fer.zemris.optjava.dz2.function.utils.Pair;
import hr.fer.zemris.optjava.dz2.function.utils.Utils;

/**
 * Function used in third task. Minimization of |Ax - b| ^ 2
 *
 * @author marko
 *
 */
public class LinearSystemFunction implements IHFunction {
    private RealMatrix A;
    private RealMatrix b;

    public LinearSystemFunction(final RealMatrix a, final RealMatrix b) {
        super();
        A = a;
        this.b = b;
    }

    public LinearSystemFunction(final String filePath) {
        try {
            Pair<RealMatrix, RealMatrix> parsed = Utils.parseSystem(filePath);
            this.A = parsed.first;
            this.b = parsed.second;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Illegal file", ex.getCause());
        }

    }

    @Override
    public int numberOfVariables() {
        return A.getColumnDimension();
    }

    @Override
    public RealVector getValue(final RealVector point) {
        if (point.getDimension() != numberOfVariables()) {
            throw new IllegalArgumentException("Invalid dimension of passed point");
        }

        RealMatrix x = MatrixUtils.createColumnRealMatrix(point.toArray());
        RealVector sol = A.multiply(x).subtract(b).getColumnVector(0);

        double err = sol.getNorm();
        return MatrixUtils.createRealVector(new double[] { err });

    }

    // 2A^T(Ax-b)
    @Override
    public RealVector getGradient(final RealVector point) {
        RealMatrix x = MatrixUtils.createColumnRealMatrix(point.toArray());

        RealMatrix tmp = A.multiply(x).subtract(b);
        RealMatrix sol = A.transpose().scalarMultiply(2).multiply(tmp);
        return sol.getColumnVector(0);
    }

    // 2AtA
    @Override
    public RealMatrix getHesseMatrix(final RealVector point) {
        return A.transpose().multiply(A).scalarMultiply(2);
    }

}
