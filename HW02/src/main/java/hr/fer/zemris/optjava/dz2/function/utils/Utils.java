package hr.fer.zemris.optjava.dz2.function.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 * Class that contains utility methods used mostly in parsing specific types of
 * function.
 * 
 * @author marko
 *
 */
public class Utils {

    public static Pair<RealMatrix, RealMatrix> parseSystem(final String filePath) throws IOException {
        List<double[]> rowsData = new ArrayList<>();
        skipComments(filePath).forEach(line -> rowsData.add(parseRow(line)));

        int rows = rowsData.size();
        int cols = rowsData.get(0).length;

        double[][] data = new double[rows][cols - 1];
        double[] sol = new double[rows];

        for (int i = 0; i < rows; ++i) {
            double[] row = rowsData.get(i);
            if (row.length != cols) {
                throw new IllegalArgumentException("Ilegal row size, expected " + cols);
            }
            for (int j = 0; j < cols - 1; ++j) {
                data[i][j] = row[j];
            }
            sol[i] = row[cols - 1];
        }

        RealMatrix A = MatrixUtils.createRealMatrix(data);
        RealMatrix b = MatrixUtils.createColumnRealMatrix(sol);
        return new Pair<>(A, b);
    }

    private static double[] parseRow(final String line) {
        if (!line.startsWith("[") || !line.endsWith("]")) {
            throw new IllegalArgumentException("Invalid line: " + line);
        }
        String[] elements = line.substring(1, line.length() - 1).split("\\s*,\\s*");
        return Arrays.stream(elements).mapToDouble(s -> Double.parseDouble(s)).toArray();
    }

    private static List<String> skipComments(final String filePath) throws IOException {
        Scanner sc = new Scanner(new File(filePath));
        List<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.startsWith("#")) {
                continue;
            }
            lines.add(line);
        }
        sc.close();
        return lines;
    }

    public static RealVector randomPoint(final int n, final double min, final double max) {
        Random rnd = new Random();
        double[] data = new double[n];
        for (int i = 0; i < n; ++i) {
            data[i] = rnd.nextDouble() * (max - min) + min;
        }

        return MatrixUtils.createRealVector(data);
    }

}
