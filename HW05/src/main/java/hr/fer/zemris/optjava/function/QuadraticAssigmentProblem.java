package hr.fer.zemris.optjava.function;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import hr.fer.zemris.optjava.ga.solution.Permutation;

public class QuadraticAssigmentProblem implements IFunction<Permutation> {

    private final int[][] distance;
    private final int[][] product;
    private final int n;

    public QuadraticAssigmentProblem(final int[][] product, final int[][] distance) {
        this.distance = distance;
        this.product = product;
        this.n = distance.length;
    }

    public QuadraticAssigmentProblem(final String path) throws IOException {
        Scanner sc = new Scanner(new File(path));
        n = Integer.parseInt(sc.nextLine().trim());

        sc.nextLine();
        distance = new int[n][];
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().trim().split("\\s+");
            distance[i] = Arrays.stream(parts).mapToInt(x -> Integer.parseInt(x.trim())).toArray();
        }

        sc.nextLine();
        product = new int[n][];
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().trim().split("\\s+");
            product[i] = Arrays.stream(parts).mapToInt(x -> Integer.parseInt(x.trim())).toArray();
        }
        sc.close();
    }

    public int getSize() {
        return n;
    }

    @Override
    public double valueAt(final Permutation sol) {
        int[] permutation = sol.values;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sum += product[i][j] * distance[permutation[i]][permutation[j]];
            }
        }
        return sum;
    }

}
