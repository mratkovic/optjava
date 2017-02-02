package hr.fer.zemris.optjava.dz2;

import java.util.Scanner;

import org.apache.commons.math3.linear.RealVector;

import hr.fer.zemris.optjava.dz2.algorithm.NumOptAlgorithms;
import hr.fer.zemris.optjava.dz2.function.IFunction;
import hr.fer.zemris.optjava.dz2.function.impl.LinearSystemFunction;
import hr.fer.zemris.optjava.dz2.function.utils.Utils;

/**
 * Runner for third task. Expected arguments are "<method> <n_iter> <file_path>"
 * where method can be grad or newton.
 *
 * @author marko
 *
 */
public class Sustav {
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
        IFunction fun = new LinearSystemFunction(filePath);
        RealVector point = Utils.randomPoint(fun.numberOfVariables(), MIN, MAX);

        if (method.equals("grad")) {
            point = NumOptAlgorithms.gradientDescent(point, fun, maxIter);
        } else if (method.equals("newton")) {
            point = NumOptAlgorithms.newtonMethod(point, fun, maxIter);
        } else {
            throw new IllegalArgumentException("Invalid method: " + method);
        }

        System.out.println("f(x): " + fun.getValue(point));

    }

}
