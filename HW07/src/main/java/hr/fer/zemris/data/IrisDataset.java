package hr.fer.zemris.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class IrisDataset implements IDataset {
    private final double[][] ins;
    private final double[][] outs;

    public IrisDataset(final double[][] ins, final double[][] outs) {
        this.ins = ins;
        this.outs = outs;
    }

    @Override
    public int numberOfSamples() {
        return ins.length;
    }

    @Override
    public double[] getInput(final int index) {
        if (index < 0 || index >= ins.length) {
            throw new IndexOutOfBoundsException("Invalid Index, number of samples" + numberOfSamples());
        }
        return ins[index];
    }

    @Override
    public double[] getOutput(final int index) {
        if (index < 0 || index >= ins.length) {
            throw new IndexOutOfBoundsException("Invalid Index, number of samples" + numberOfSamples());
        }
        return outs[index];
    }

    private static double[] parseLineDoubles(final String line) {
        String noBrackets = line.substring(1, line.length() - 1);
        return Arrays.stream(noBrackets.split(",")).map(String::trim).mapToDouble(Double::parseDouble).toArray();
    }

    public static IrisDataset load(final String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(path));

        List<double[]> ins = new LinkedList<>();
        List<double[]> outs = new LinkedList<>();

        while (sc.hasNextLine()) {
            String[] parts = sc.nextLine().split(":");
            ins.add(parseLineDoubles(parts[0]));
            outs.add(parseLineDoubles(parts[1]));
        }
        sc.close();
        return new IrisDataset(ins.toArray(new double[][] {}), outs.toArray(new double[][] {}));
    }
}
