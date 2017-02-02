package hr.fer.zemris.optimizations.pso;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optimizations.IFunction;

public class PSO {

    Swarm swarm;
    int maxIterations;
    IFunction f;
    IFunction fitnessFunction;
    Random rnd;
    double wMax, wMin;
    double c1, c2;
    final double[] vMin, vMax;

    public PSO(final IFunction f, final Swarm swarm, final boolean minimize, final Random rnd, final double vMin,
            final double vMax, final double c1, final double c2, final double wMin, final double wMax) {
        super();
        this.f = f;
        this.swarm = swarm;
        this.rnd = rnd;

        this.fitnessFunction = new IFunction() {
            @Override
            public double valueAt(final double[] values) {
                int a = minimize ? -1 : 1;
                return a * f.valueAt(values);
            }

            @Override
            public int nVariables() {
                return f.nVariables();
            }
        };
        this.vMin = new double[swarm.particleSize];
        this.vMax = new double[swarm.particleSize];
        Arrays.fill(this.vMin, vMin);
        Arrays.fill(this.vMax, vMax);
        this.wMin = wMin;
        this.wMax = wMax;

        this.c1 = c1;
        this.c2 = c2;
    }

    public double[] run(final int maxIter, final double errorLimit) {
        int iter = 0;
        while (iter < maxIter && swarm.getBest().value > errorLimit) {
            swarm.evaluate(f, fitnessFunction);

            double inertionFactor = (wMax - wMin) * (1 - 1. * iter / maxIter) + wMin;
            // evaluate
            for (Particle p : swarm) {
                p.updatePersonalBest();
                if (p.fitness >= swarm.getBest().fitness) {
                    swarm.setBest(p);
                }
            }
            // move swarm
            for (Particle p : swarm) {
                p.updatePosition(inertionFactor, c1, c2, vMin, vMax, rnd);
            }

            if (iter % 100 == 0) {
                System.out.printf("%6d iter\tcost: %4.6f\n", iter, swarm.best.value);
            }
            iter++;
        }

        return swarm.best.getPosition();

    }
}
