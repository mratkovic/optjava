package hr.fer.zemris.optjava.dz13.ant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AntMap {

    public static char FOOD = '1';

    public int width;
    public int height;
    public boolean[][] food;
    public boolean[][] originalState;

    public AntMap(final int width, final int height, final boolean[][] food) {
        super();
        this.width = width;
        this.height = height;
        this.originalState = food;
        this.food = arrayCopy(food);
    }

    public static AntMap parseFile(final String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        String[] size = lines.get(0).trim().split("x");

        int height = Integer.parseInt(size[0]);
        int width = Integer.parseInt(size[1]);

        if (lines.size() != height + 1) {
            throw new IllegalArgumentException("Invalid map file, number of lines != height");
        }

        boolean[][] food = new boolean[height][width];

        for (int i = 0; i < height; ++i) {
            char[] line = lines.get(i + 1).trim().toCharArray();

            if (line.length != width) {
                throw new IllegalArgumentException("Invalid map file, line.length != width");
            }

            for (int j = 0; j < width; ++j) {
                food[i][j] = line[j] == FOOD;
            }
        }

        return new AntMap(width, height, food);
    }

    public AntMap copy() {
        return new AntMap(width, height, arrayCopy(food));
    }

    private boolean[][] arrayCopy(final boolean[][] arr) {
        boolean[][] newArr = new boolean[arr.length][arr[0].length];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                newArr[i][j] = arr[i][j];
            }
        }
        return newArr;
    }

    public void restoreMap() {
        food = arrayCopy(originalState);
    }

    public boolean isFoodAhead(final Ant ant) {
        int x = ant.nextX(width);
        int y = ant.nextY(height);

        return food[x][y];
    }

}
