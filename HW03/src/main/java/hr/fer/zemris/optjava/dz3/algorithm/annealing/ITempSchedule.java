package hr.fer.zemris.optjava.dz3.algorithm.annealing;

public interface ITempSchedule {

    public double getNextTemperature();

    public int getInnerLoopCounter();

    public int getOuterLoopCounter();

}
