package hr.fer.zemris.optjava.dz2.function.impl;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import hr.fer.zemris.optjava.dz2.function.IHFunction;
import hr.fer.zemris.optjava.dz2.function.utils.Pair;
import hr.fer.zemris.optjava.dz2.function.utils.Utils;

/**
 * Function used in task 4. As error function 1/2 * sum of square offsets is
 * taken.
 *
 * @author marko
 *
 */
public class CoefficientsFunction implements IHFunction {
    private double[][] inputs;
    private double[] outputs;

    public CoefficientsFunction(final String filePath) {
        try {
            Pair<RealMatrix, RealMatrix> parsed = Utils.parseSystem(filePath);
            this.inputs = parsed.first.getData();
            this.outputs = parsed.second.getColumn(0);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Illegal file", ex.getCause());
        }
    }

    @Override
    public int numberOfVariables() {
        return 6;
    }

    public int numberOfSamples() {
        return inputs.length;
    }

    @Override
    public RealVector getValue(final RealVector point) {
        double err = 0;
        double[] pt = point.toArray();

        for (int i = 0; i < inputs.length; ++i) {
            double val = evalRowAt(pt, i) - outputs[i];
            err += val * val; // square error
        }
        err *= 0.5;
        return MatrixUtils.createRealVector(new double[] { err });
    }

    private double evalRowAt(final double[] pt, final int i) {
        double[] x = inputs[i];
        double val = pt[0] * x[0] + pt[1] * Math.pow(x[0], 3) * x[1]
                + pt[2] * Math.exp(pt[3] * x[2]) * (1 + Math.cos(pt[4] * x[3])) + pt[5] * x[3] * Math.pow(x[4], 2);
        return val;
    }

    @Override
    public RealVector getGradient(final RealVector point) {
        double[] grad = new double[numberOfVariables()];
        double[] pt = point.toArray();

        for (int i = 0; i < inputs.length; i++) {
            double[] x = inputs[i];
            double val = evalRowAt(pt, i) - outputs[i];
            double expFactor = Math.exp(pt[3] * x[2]);
            double cosfactor = (1 + Math.cos(pt[4] * x[3]));

            grad[0] += val * x[0];
            grad[1] += val * Math.pow(x[0], 3) * x[1];
            grad[2] += val * expFactor * cosfactor;
            grad[3] += val * pt[2] * expFactor * cosfactor * x[2];
            grad[4] += val * pt[2] * expFactor * (-1) * Math.sin(pt[4] * x[3]) * x[3];
            grad[5] += val * x[3] * Math.pow(x[4], 2);

        }
        return MatrixUtils.createRealVector(grad).unitVector();
    }

    @Override
    public RealMatrix getHesseMatrix(final RealVector point) {
        double[][] hesse = new double[numberOfVariables()][numberOfVariables()];
        double[] pt = point.toArray();

        for (int i = 0; i < inputs.length; i++) {
            double[] x = inputs[i];
            double val = evalRowAt(pt, i) - outputs[i];

            double expFactor = Math.exp(pt[3] * x[2]);
            double cosfactor = (1 + Math.cos(pt[4] * x[3]));

            // tmp vars (df / d_)
            double da = x[0];
            double db = Math.pow(x[0], 3) * x[1];
            double dc = expFactor * cosfactor;
            double dd = pt[2] * expFactor * cosfactor * x[2];
            double de = pt[2] * expFactor * (-1) * Math.sin(pt[4] * x[3]) * x[3];
            double df = x[3] * Math.pow(x[4], 2);

            hesse[0][0] += da * da;
            hesse[1][1] += db * db;
            hesse[2][2] += dc * dc;
            hesse[3][3] += dd * dd + val * dd * x[2];
            hesse[4][4] += de * de + val * pt[2] * expFactor * (-1) * Math.cos(pt[4] * x[3]) * x[3];
            hesse[5][5] += df * df;

            // I know matrix is symetric but still...
            hesse[0][1] += da * db;
            hesse[0][2] += da * dc;
            hesse[0][3] += da * dd;
            hesse[0][4] += da * de;
            hesse[0][5] += da * df;

            hesse[1][0] += db * da;
            hesse[1][2] += db * dc;
            hesse[1][3] += db * dd;
            hesse[1][4] += db * de;
            hesse[1][5] += db * df;

            hesse[2][0] += dc * da;
            hesse[2][1] += dc * db;
            hesse[2][3] += dc * dd + val * dc * x[2];
            hesse[2][4] += dc * de + val * expFactor * (-1) * Math.sin(pt[4] * x[3]) * x[3];
            hesse[2][5] += dc * df;

            hesse[3][0] += dd * da;
            hesse[3][1] += dd * db;
            hesse[3][2] += dd * dc + val * dc * x[2];
            hesse[3][4] += dd * de + val * de * x[2];
            hesse[3][5] += dd * df;

            hesse[4][0] += de * da;
            hesse[4][1] += de * db;
            hesse[4][2] += de * dc + val * expFactor * (-1) * Math.sin(pt[4] * x[3]) * x[3];
            hesse[4][3] += de * dd + val * de * x[2];
            hesse[4][5] += de * df;

            hesse[5][0] += df * da;
            hesse[5][1] += df * db;
            hesse[5][2] += df * dc;
            hesse[5][3] += df * dd;
            hesse[5][4] += df * de;

        }

        return MatrixUtils.createRealMatrix(hesse);
    }
}
