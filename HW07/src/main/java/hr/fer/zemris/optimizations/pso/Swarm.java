package hr.fer.zemris.optimizations.pso;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optimizations.IFunction;

public class Swarm implements Iterable<Particle> {
    List<Particle> swarm;

    Particle best;
    int populationSize;
    int particleSize;

    public Swarm(final int populationSize, final int particleSize) {
        super();
        this.populationSize = populationSize;
        this.particleSize = particleSize;
        swarm = new LinkedList<>();
        best = new Particle(particleSize);
    }

    public void initializeParticles(final Random rnd, final double[] xMin, final double[] xMax, final double[] vMin,
            final double[] vMax) {

        swarm = new LinkedList<>();
        for (int i = 0; i < populationSize; i++) {
            Particle p = new Particle(particleSize);
            p.randomize(xMin, xMax, vMin, vMax, rnd);
            swarm.add(p);
        }
    }

    public void initializeParticles(final Random rnd, final double xMin, final double xMax, final double vMin,
            final double vMax) {
        double[] xMins = new double[particleSize];
        double[] xMaxs = new double[particleSize];
        double[] vMins = new double[particleSize];
        double[] vMaxs = new double[particleSize];

        Arrays.fill(xMins, xMin);
        Arrays.fill(xMaxs, xMax);
        Arrays.fill(vMins, vMin);
        Arrays.fill(vMaxs, vMax);

        initializeParticles(rnd, xMins, xMaxs, vMins, vMaxs);
    }

    public void initializeNeighborhood(final int d) {
        if (d >= populationSize / 2) {
            initializeGlobalNeighborhood();
            return;
        }

        for (int i = 0; i < populationSize; ++i) {
            for (int j = -d; j <= d; ++j) {
                int index = (i + j + populationSize) % populationSize;
                if (index == i) {
                    continue;
                }
                swarm.get(i).addNeighbor(swarm.get(index));
            }
        }
    }

    public void initializeNeighborhood(final String algo) {
        if (algo.equals("pso-a")) {
            initializeGlobalNeighborhood();
        } else if (algo.matches("pso-b-\\d+")) {
            String n = algo.split("-")[2];
            initializeNeighborhood(Integer.parseInt(n));
        } else {
            throw new IllegalArgumentException("Invalid neigborhood " + algo);
        }
    }

    public void initializeGlobalNeighborhood() {
        for (Particle p : swarm) {
            p.addNeighbor(best);
        }
    }

    public void evaluate(final IFunction func, final IFunction fitness) {
        for (Particle p : swarm) {
            p.value = func.valueAt(p.getPosition());
            p.fitness = fitness.valueAt(p.getPosition());
        }
        // desc
        swarm.sort((e1, e2) -> e2.compareTo(e1));
    }

    public Particle getBest() {
        return best;
    }

    public void setBest(final Particle p) {
        best.set(p);

    }

    @Override
    public Iterator<Particle> iterator() {
        return swarm.iterator();
    }

}
