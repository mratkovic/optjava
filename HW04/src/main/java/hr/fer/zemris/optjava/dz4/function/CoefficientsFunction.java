package hr.fer.zemris.optjava.dz4.function;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Function used in task 4. As error function mean sum of square offsets is
 * used.
 *
 * @author marko
 *
 */
public class CoefficientsFunction implements IFunction {
    private double[][] inputs;
    private double[] outputs;

    public CoefficientsFunction(final String filePath) {
        try {
            parseSystem(filePath);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Illegal file", ex.getCause());
        }
    }

    public int numberOfSamples() {
        return inputs.length;
    }

    public int numberOfVariables() {
        return 6;
    }

    @Override
    public double valueAt(final double[] values) {
        double err = 0;
        for (int i = 0; i < inputs.length; ++i) {
            double val = evalRowAt(values, i) - outputs[i];
            err += val * val;
        }
        return err / numberOfSamples();
    }

    private double evalRowAt(final double[] pt, final int i) {
        double[] x = inputs[i];
        double val = pt[0] * x[0] + pt[1] * Math.pow(x[0], 3) * x[1]
                + pt[2] * Math.exp(pt[3] * x[2]) * (1 + Math.cos(pt[4] * x[3])) + pt[5] * x[3] * Math.pow(x[4], 2);
        return val;
    }

    private void parseSystem(final String filePath) throws IOException {
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
        inputs = data;
        outputs = sol;
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
}
