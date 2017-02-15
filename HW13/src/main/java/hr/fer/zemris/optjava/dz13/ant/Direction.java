package hr.fer.zemris.optjava.dz13.ant;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;

    private static Direction[] vals = values();
    private static int[] dx = { -1, 0, 1, 0 };
    private static int[] dy = { 0, 1, 0, -1 };

    public int getDx() {
        return dx[this.ordinal()];
    }

    public int getDy() {
        return dy[this.ordinal()];
    }

    public Direction rotateRight() {
        return rotate(+1);
    }

    public Direction rotateLeft() {
        return rotate(-1);
    }

    private Direction rotate(final int by) {
        return vals[(this.ordinal() + by + vals.length) % vals.length];
    }
}
