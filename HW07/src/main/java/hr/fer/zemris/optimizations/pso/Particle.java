package hr.fer.zemris.optimizations.pso;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optimizations.SingleObjectiveSolution;

public class Particle extends SingleObjectiveSolution {
    private double[] velocity;
    private double[] position;

    private double bestFitness = -Double.MAX_VALUE;
    private double[] bestPosition;
    private List<Particle> neighbors = new LinkedList<>();

    public Particle(final int len) {
        this.velocity = new double[len];
        this.position = new double[len];
        this.bestPosition = new double[len];
    }

    public Particle(final double[] speed, final double[] position) {
        this.velocity = speed;
        this.position = position;
    }

    public void randomize(final double[] xMin, final double[] xMax, final double[] vMin, final double[] vMax,
            final Random rnd) {
        for (int i = 0; i < velocity.length; i++) {
            velocity[i] = rnd.nextDouble() * (vMax[i] - vMin[i]) + vMin[i];
            bestPosition[i] = position[i] = rnd.nextDouble() * (xMax[i] - xMin[i]) + xMin[i];
        }
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(final double[] position) {
        this.position = position;
    }

    @Override
    public Particle duplicate() {
        Particle p = new Particle(Arrays.copyOf(velocity, velocity.length), Arrays.copyOf(position, position.length));
        p.bestFitness = this.bestFitness;
        p.bestPosition = Arrays.copyOf(bestPosition, bestPosition.length);
        p.neighbors = neighbors;
        p.fitness = this.fitness;
        p.value = this.value;
        return p;
    }

    public void set(final Particle p) {
        this.velocity = Arrays.copyOf(p.velocity, p.velocity.length);
        this.position = Arrays.copyOf(p.position, p.position.length);
        this.bestFitness = p.bestFitness;
        this.bestPosition = Arrays.copyOf(p.bestPosition, p.bestPosition.length);
        this.neighbors = p.neighbors;
        this.fitness = p.fitness;
        this.value = p.value;

    }

    public void addNeighbor(final Particle p) {
        neighbors.add(p);
    }

    public double[] getBestPosition() {
        return bestPosition;
    }

    public double[] getBestNeighbor() {
        Particle best = neighbors.stream().max((e1, e2) -> Double.compare(e1.bestFitness, e1.bestFitness)).orElse(null);
        return best.position;
    }

    public void updatePersonalBest() {
        if (bestFitness <= fitness) {
            bestFitness = fitness;
            bestPosition = Arrays.copyOf(position, position.length);
        }
    }

    public void updatePosition(final double inertionFactor, final double c1, final double c2, final double[] vMin,
            final double[] vMax, final Random rnd) {
        double[] gBest = getBestNeighbor();
        double[] pBest = getBestPosition();

        for (int i = 0; i < position.length; i++) {

            double r1 = rnd.nextDouble();
            double r2 = rnd.nextDouble();

            double individual = c1 * r1 * (pBest[i] - position[i]);
            double social = c2 * r2 * (gBest[i] - position[i]);
            velocity[i] = velocity[i] * inertionFactor + individual + social;
            velocity[i] = Math.min(velocity[i], vMax[i]);
            velocity[i] = Math.max(velocity[i], vMin[i]);

            position[i] += velocity[i];
        }
    }
}
