package hr.fer.zemris.optjava.dz2.algorithm;

import java.util.Arrays;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import hr.fer.zemris.optjava.dz2.function.IFunction;
import hr.fer.zemris.optjava.dz2.function.IHFunction;

/**
 * Class that implements numeric optimisation methods such as GradientDescent
 * and NewtonMethod.
 *
 * @author marko
 *
 */
public class NumOptAlgorithms {
    private static final double EPS = 1e-3;

    private enum Method {
        GRADIENT_DESCENT, NEWTON
    }

    public static RealVector gradientDescent(final RealVector point, final IFunction function, final int maxIter) {
        return minimize(point, function, maxIter, Method.GRADIENT_DESCENT);
    }

    public static RealVector newtonMethod(final RealVector point, final IFunction function, final int maxIter) {
        return minimize(point, function, maxIter, Method.NEWTON);
    }

    /**
     * Method that minimizes given function using selected method
     * 
     * @param point
     * @param function
     * @param maxIter
     * @param method
     * @return
     */
    private static RealVector minimize(final RealVector point, final IFunction function, final int maxIter,
            final Method method) {

        RealVector x = point.copy();
        System.out.println(x + "; f(x)=" + function.getValue(x));

        int it = 0;
        while (it < maxIter && !isNearOptimum(x, function)) {
            it++;

            RealVector d = getDirectionVector(function, x, method);
            double lambda = bisectionAlgorithm(function, x, d, maxIter);
            x = x.add(d.mapMultiply(lambda));
            System.out.println(x + "; f(x)=" + function.getValue(x));
        }
        System.out.println("\n\nDone...");
        System.out.println("Iter count: " + it);
        System.out.println("Solution: " + x);
        return x;
    }

    /**
     * Method that returns direction vector depending of optimization method.
     * For gradient descent it returns -gradient, for newton method returns (-1)
     * times H^-1 times gradient
     *
     * @param function
     * @param x
     * @param method
     * @return
     */
    private static RealVector getDirectionVector(final IFunction function, final RealVector x, final Method method) {
        if (method == Method.GRADIENT_DESCENT) {
            return function.getGradient(x).mapMultiply(-1).unitVector();

        } else {

            RealMatrix gradient = MatrixUtils.createColumnRealMatrix(function.getGradient(x).toArray());
            RealMatrix hesse = ((IHFunction) function).getHesseMatrix(x);
            RealMatrix invHesse = new LUDecomposition(hesse).getSolver().getInverse();

            RealMatrix sol = invHesse.multiply(gradient);
            RealVector solVector = sol.getColumnVector(0);
            return solVector.mapMultiply(-1).unitVector();
        }
    }

    /**
     * Checks exit condition if given point is near optimum of passed function.
     * Point is near optimum if all compontnt of gradint are smaller that EPS
     * constant
     *
     * @param x
     * @param function
     * @return
     */
    private static boolean isNearOptimum(final RealVector x, final IFunction function) {
        double[] grad = function.getGradient(x).toArray();
        return Arrays.stream(grad).allMatch(val -> Math.abs(val) < EPS);

    }

    /**
     * Bisection algorithm used to find optimal lambda as scalar multiplier for
     * given direction vector.
     *
     * @param function
     * @param point
     * @param d
     * @param maxIter
     * @return
     */
    private static double bisectionAlgorithm(final IFunction function, final RealVector point, final RealVector d,
            int maxIter) {

        RealVector x = point.copy();
        double lo = 0;
        double hi = findLambdaUpper(function, point, d);

        while (maxIter-- > 0) {
            double mid = (lo + hi) / 2;
            x = point.add(d.mapMultiply(mid));

            double der = function.getGradient(x).dotProduct(d);
            if (Math.abs(der) < EPS) {
                return mid;
            }

            if (der < 0) {
                lo = mid;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    /**
     * Find upper bound for lambda in bisection algorithm.
     *
     * @param function
     * @param point
     * @param d
     * @return
     */
    private static double findLambdaUpper(final IFunction function, final RealVector point, final RealVector d) {
        double lambdaUpper = 1;
        RealVector x = point.copy();

        while (function.getGradient(x).dotProduct(d) < 0) {
            lambdaUpper *= 2;
            x = point.add(d.mapMultiply(lambdaUpper));
        }
        return lambdaUpper;
    }

}
