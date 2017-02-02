package hr.fer.zemris.optjava.dz4.part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import hr.fer.zemris.optjava.ga.decoder.IDecoder;

public class BoxSolutionDecoder implements IDecoder<BoxSolution> {
    int maxHeight;
    int[] stickHeights;

    public BoxSolutionDecoder(final int maxHeight, final int[] stickHeights) {
        super();
        this.maxHeight = maxHeight;
        this.stickHeights = stickHeights;
    }

    /**
     * Read problem from file.
     *
     * @throws FileNotFoundException
     */
    public BoxSolutionDecoder(final int maxHeight, final String filepath) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filepath));

        String line = sc.nextLine();
        line = line.substring(1, line.length() - 1); // remove []
        String[] parts = line.split(",");

        stickHeights = Arrays.stream(parts).map(String::trim).mapToInt(Integer::parseInt).toArray();

        this.maxHeight = maxHeight;
        sc.close();
    }

    /**
     * Decode solution as permutation of sticks. Generate solution by grouping
     * them together in a row. Return percentage of empty space per columnt as
     * double array.
     */
    @Override
    public double[] decode(final BoxSolution sol) {
        double[] spaceLeft = null;
        List<Integer> heights = new ArrayList<>();

        int currentHeight = 0;
        for (int i : sol.values) {
            int stick = stickHeights[i];

            if (currentHeight + stick > maxHeight) {
                heights.add(currentHeight);
                currentHeight = stick;

            } else {
                currentHeight += stick;
            }
        }
        if (currentHeight != 0) {
            heights.add(currentHeight);
        }
        spaceLeft = heights.stream().mapToDouble(i -> 1 - i / maxHeight).toArray();
        return spaceLeft;
    }

    @Override
    public void decode(final BoxSolution sol, final double[] spaceLeft) {
        throw new RuntimeException("Not implemented");

    }

    @Override
    public void printSolution(final BoxSolution best) {
        int cnt = 0;
        List<Integer> row = new ArrayList<>();
        int currentHeight = 0;
        for (int i : best.values) {
            int stick = stickHeights[i];

            if (currentHeight + stick > maxHeight) {
                printBucket(cnt++, row);
                row.clear();
                row.add(stick);
                currentHeight = stick;

            } else {
                currentHeight += stick;
                row.add(stick);
            }
        }
        if (currentHeight != 0) {
            printBucket(cnt++, row);
        }

    }

    private void printBucket(final int i, final List<Integer> row) {
        String line = row.stream().map(el -> String.valueOf(el)).collect(Collectors.joining(", "));
        System.out.println(i + ": " + line);

    }

}
