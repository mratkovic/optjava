package hr.fer.zemris.optjava.dz2;

import java.util.Scanner;

import org.apache.commons.math3.linear.RealVector;

import hr.fer.zemris.optjava.dz2.algorithm.NumOptAlgorithms;
import hr.fer.zemris.optjava.dz2.function.impl.CoefficientsFunction;
import hr.fer.zemris.optjava.dz2.function.utils.Utils;

/**
 * Runner for forth task. Expected arguments are "<method> <n_iter> <file_path>"
 * where method can be grad or newton.
 *
 * @author marko
 *
 */
public class Prijenosna {
    /**
     * Random point range.
     */
    private static double MIN = -5;
    private static double MAX = 5;

    public static void main(final String[] args) {
        if (args.length != 0 && args.length != 3) {
            throw new IllegalArgumentException("Invalid call; Expected <method> <n_iter> <file_path>");
        }

        String method = null, maxIterStr = null, filePath = null;

        if (args.length == 0) {
            // ask for arguments
            Scanner sc = new Scanner(System.in);
            System.out.println("Method [grad/newton]:");
            method = sc.nextLine().trim();

            System.out.println("Maximum number of iterations:");
            maxIterStr = sc.nextLine().trim();

            System.out.println("File path:");
            filePath = sc.nextLine().trim();
        }

        if (args.length == 3) {
            method = args[0];
            maxIterStr = args[1];
            filePath = args[2];
        }

        int maxIter = Integer.parseInt(maxIterStr);
        CoefficientsFunction fun = new CoefficientsFunction(filePath);
        RealVector point = Utils.randomPoint(fun.numberOfVariables(), MIN, MAX);

        if (method.equals("grad")) {
            point = NumOptAlgorithms.gradientDescent(point, fun, maxIter);
        } else if (method.equals("newton")) {
            point = NumOptAlgorithms.newtonMethod(point, fun, maxIter);
        } else {
            throw new IllegalArgumentException("Invalid method: " + method);
        }

        double err = fun.getValue(point).getEntry(0);
        System.out.println("f(x): " + err);
        // err is 1/2* sum of squares
        System.out.println("MSE: " + Math.sqrt(2 * err) / fun.numberOfSamples());
    }

}
