package hr.fer.zemris.optjava.dz13.ant.gui;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.AntMap;
import hr.fer.zemris.optjava.dz13.ant.Direction;

public class BlockingAnt extends Ant {
    private final Ant ant;
    boolean wait;
    boolean run;

    boolean restart;

    private final List<IAntListener> listeners;

    public BlockingAnt(final int x, final int y, final Direction direction, final Ant ant,
            final List<IAntListener> listeners) {
        super(x, y, direction);
        this.ant = ant;
        this.listeners = listeners;

        wait = true;
    }

    public BlockingAnt(final int x, final int y, final Direction direction) {
        super(x, y, direction);
        ant = new Ant(x, y, direction);

        listeners = new ArrayList<>();
        wait = true;
    }

    @Override
    public void move(final AntMap map) {
        nofifyListeners();
        waitForAction();
        ant.move(map);
    }

    private void waitForAction() {
        while (wait) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (run) {
                break;
            }
            if (restart) {
                restart = false;
                wait = true;
                throw new AntRestartException();
            }
        }
        wait = true;
    }

    @Override
    public void rotateRight() {
        nofifyListeners();
        waitForAction();
        ant.rotateRight();
    }

    @Override
    public void rotateLeft() {
        nofifyListeners();
        waitForAction();
        ant.rotateLeft();
    }

    public void addListener(final IAntListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    private void nofifyListeners() {
        for (IAntListener listener : listeners) {
            listener.update(this.ant);
        }
    }

    public void resume() {
        wait = false;
    }

    @Override
    public void setX(final int x) {
        ant.setX(x);
    }

    @Override
    public void setY(final int y) {
        ant.setY(y);
    }

    @Override
    public void setFoodEaten(final int foodEaten) {
        ant.setFoodEaten(foodEaten);
    }

    @Override
    public int getX() {
        return ant.getX();
    }

    @Override
    public int getY() {
        return ant.getY();
    }

    @Override
    public int getFoodEaten() {
        return ant.getFoodEaten();
    }

    @Override
    public int getStepsTaken() {
        return ant.getStepsTaken();
    }

    @Override
    public void setStepsTaken(final int stepsTaken) {
        ant.setStepsTaken(stepsTaken);
    }

    @Override
    public int nextX(final int max) {
        return ant.nextX(max);
    }

    @Override
    public int nextY(final int max) {
        return ant.nextY(max);
    }

    @Override
    public Ant copy() {
        return new BlockingAnt(getX(), getY(), getDirection(), ant, listeners);
    }

    public void setReset() {
        ant.reset();
        restart = true;
        run = false;
        wait = false;

    }

    @Override
    public void reset() {
        ant.reset();
        run = false;
        wait = true;

    }

    @Override
    public Direction getDirection() {
        return ant.getDirection();
    }

    @Override
    public void setDirection(final Direction direction) {
        ant.setDirection(direction);
    }

    public void runAll() {
        run = true;
        wait = false;
    }

}
