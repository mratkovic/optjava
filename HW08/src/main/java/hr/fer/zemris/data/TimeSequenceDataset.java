package hr.fer.zemris.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class TimeSequenceDataset implements IDataset {
    private final double[][] ins;
    private final double[][] outs;

    public TimeSequenceDataset(final double[][] ins, final double[][] outs) {
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

    public static TimeSequenceDataset load(final String path, final int inSize, int totalSamples)
            throws FileNotFoundException {
        List<Double> data = new ArrayList<>();
        Scanner sc = new Scanner(new File(path));

        while (sc.hasNextLine()) {
            double x = Double.parseDouble(sc.nextLine().trim());
            data.add(x);
        }
        sc.close();
        if (totalSamples == -1) {
            totalSamples = data.size();
        }

        double min = data.stream().min((a, b) -> a.compareTo(b)).orElse(-Double.MAX_VALUE);
        double max = data.stream().min((a, b) -> b.compareTo(a)).orElse(Double.MAX_VALUE);
        Function<Double, Double> normalize = x -> 2 * (x - min) / (max - min) - 1;
        for (int i = 0; i < data.size(); ++i) {
            data.set(i, normalize.apply(data.get(i)));
        }

        // make samples
        int nSamples = totalSamples - inSize;
        double[][] input = new double[nSamples][inSize];
        double[][] output = new double[nSamples][1];
        LinkedList<Double> window = new LinkedList<>(data.subList(0, inSize));
        int current = inSize;

        for (int i = 0; i < nSamples; ++i) {
            double next = data.get(current++);
            input[i] = window.stream().mapToDouble(x -> x).toArray();
            output[i][0] = next;
            window.removeFirst();
            window.addLast(next);

        }

        return new TimeSequenceDataset(input, output);
    }
}
