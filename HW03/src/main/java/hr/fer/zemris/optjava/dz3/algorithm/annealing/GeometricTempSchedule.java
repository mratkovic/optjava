package hr.fer.zemris.optjava.dz3.algorithm.annealing;

public class GeometricTempSchedule implements ITempSchedule {

    private final double alpha;
    private double tCurrent;
    private final int innerLimit;
    private final int outerLimit;

    public GeometricTempSchedule(final double alpha, final double tInitial, final int innerLimit,
            final int outerLimit) {

        this.alpha = alpha;
        this.tCurrent = tInitial;
        this.innerLimit = innerLimit;
        this.outerLimit = outerLimit;
    }

    @Override
    public double getNextTemperature() {
        double next = tCurrent;
        tCurrent *= alpha;
        return next;
    }

    @Override
    public int getInnerLoopCounter() {
        return innerLimit;
    }

    @Override
    public int getOuterLoopCounter() {
        return outerLimit;
    }

}
