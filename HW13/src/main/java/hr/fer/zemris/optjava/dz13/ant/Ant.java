package hr.fer.zemris.optjava.dz13.ant;

public class Ant {
    private int x;
    private int y;

    private Direction direction;
    private int foodEaten;

    private int stepsTaken;

    public Ant(final int x, final int y, final Direction direction) {
        super();
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int nextX(final int max) {
        return clip(x + direction.getDx(), 0, max);
    }

    public int nextY(final int max) {
        return clip(y + direction.getDy(), 0, max);
    }

    public void move(final AntMap map) {
        x = nextX(map.width);
        y = nextY(map.height);

        foodEaten += map.food[x][y] ? 1 : 0;
        map.food[x][y] = false;
        stepsTaken++;

    }

    /**
     * Clip x value to range [min, max> or [min, max-1]
     *
     * @param x
     * @param min
     * @param max
     * @return
     */
    private static int clip(final int x, final int min, final int max) {
        return Math.min(Math.max(x, min), max - 1);
    }

    public void rotateRight() {
        direction = direction.rotateRight();
        stepsTaken++;
    }

    public void rotateLeft() {
        direction = direction.rotateLeft();
        stepsTaken++;
    }

    public Ant copy() {
        return new Ant(x, y, direction);
    }

    public void setX(final int x) {
        this.x = x;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public void setFoodEaten(final int foodEaten) {
        this.foodEaten = foodEaten;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFoodEaten() {
        return foodEaten;
    }

    public int getStepsTaken() {
        return stepsTaken;
    }

    public void setStepsTaken(final int stepsTaken) {
        this.stepsTaken = stepsTaken;
    }

    public void reset() {
        stepsTaken = 0;
        foodEaten = 0;
        x = y = 0;
        direction = Direction.RIGHT;

    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

}
