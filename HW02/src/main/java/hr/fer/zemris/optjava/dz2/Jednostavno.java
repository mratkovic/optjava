package hr.fer.zemris.optjava.dz2;

import java.util.Random;
import java.util.Scanner;

import org.apache.commons.math3.linear.MatrixUtils;

import hr.fer.zemris.optjava.dz2.algorithm.NumOptAlgorithms;
import hr.fer.zemris.optjava.dz2.function.IFunction;
import hr.fer.zemris.optjava.dz2.function.impl.Function1;
import hr.fer.zemris.optjava.dz2.function.impl.Function2;

/**
 * Runner for first 2 tasks. Expects arguments <task> <n_iter> [point_x point_y]
 *
 * @author marko
 *
 */
public class Jednostavno {
    /**
     * Random point range.
     */
    private static double MIN = -5;
    private static double MAX = 5;

    public static void main(final String[] args) {
        if (args.length != 0 && args.length != 2 && args.length != 4) {
            throw new IllegalArgumentException("Invalid call; Expected <task> <n_iter> [point_x point_y]");
        }

        String taskStr = null, maxIterStr = null, pointStr = null;

        if (args.length == 0) {
            // ask for arguments
            Scanner sc = new Scanner(System.in);
            System.out.println("Task [1a, 1b, 2a, 2b]:");
            taskStr = sc.nextLine().trim();

            System.out.println("Maximum number of iterations:");
            maxIterStr = sc.nextLine().trim();

            System.out.println("Start point (x, y space seperated; blank line to skip):");
            pointStr = sc.nextLine().trim();
            if (pointStr.isEmpty()) {
                pointStr = null;
            }
        }

        if (args.length > 2) {
            taskStr = args[0];
            maxIterStr = args[1];
        }
        if (args.length == 4) {
            pointStr = args[2] + " " + args[3];
        }

        int maxIter = Integer.parseInt(maxIterStr);
        double[] point = parseStartPoint(pointStr);

        char taskNum = taskStr.charAt(0);
        IFunction fun = selectFunction(taskNum);

        runSolution(taskStr, maxIter, point, fun);

    }

    private static void runSolution(final String taskStr, final int maxIter, final double[] point,
            final IFunction fun) {

        if (taskStr.equals("1a") || taskStr.equals("2a")) {
            NumOptAlgorithms.gradientDescent(MatrixUtils.createRealVector(point), fun, maxIter);

        } else if (taskStr.equals("1b") || taskStr.equals("2b")) {
            NumOptAlgorithms.newtonMethod(MatrixUtils.createRealVector(point), fun, maxIter);

        } else {
            throw new IllegalArgumentException("Invalid task:" + taskStr);
        }

    }

    /**
     * Parse input data. If argument is empty or null, random generated point is
     * returned.
     *
     * @param pointStr
     * @return
     */
    private static double[] parseStartPoint(final String pointStr) {
        Random rnd = new Random();

        double x1 = rnd.nextDouble() * (MAX - MIN) + MIN;
        double x2 = rnd.nextDouble() * (MAX - MIN) + MIN;

        double[] point = new double[] { x1, x2 };

        if (pointStr != null && !pointStr.isEmpty()) {
            String[] parts = pointStr.split("\\s+");
            point[0] = Double.parseDouble(parts[0].trim());
            point[1] = Double.parseDouble(parts[1].trim());
        }
        return point;
    }

    /**
     * Task function selection method.
     *
     * @param taskNum
     * @return
     */
    private static IFunction selectFunction(final char taskNum) {
        if (taskNum == '1') {
            return new Function1();
        } else if (taskNum == '2') {
            return new Function2();
        } else {
            throw new IllegalArgumentException("Invalid task num:" + taskNum);
        }
    }
}
