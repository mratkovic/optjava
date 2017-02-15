package hr.fer.zemris.optjava.dz13.ant.ga.solution;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.AntMap;
import hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes.AbstractGPNode;
import hr.fer.zemris.optjava.ga.AbstractGASolution;

public class AntGPSolution extends AbstractGASolution {
    private static final double PLAGIARISM_PENALTY = 0.9;

    AbstractGPNode data;

    int score;
    int parentScore;

    public AntGPSolution(final AbstractGPNode data) {
        super();
        this.data = data;
    }

    public AntGPSolution(final AbstractGPNode data, final double fitness, final int score, final int parentScore) {
        super();
        this.data = data;
        this.score = score;
        this.parentScore = parentScore;
        this.fitness = fitness;
    }

    public AbstractGPNode getData() {
        return data;
    }

    @Override
    public AbstractGASolution duplicate() {
        return new AntGPSolution(data.copy(), fitness, score, parentScore);
    }

    public int execute(final AntMap map, final Ant ant) {
        return data.execute(map, ant);
    }

    @Override
    public void adjustFitness() {
        if (score == parentScore) {
            fitness *= PLAGIARISM_PENALTY;
        }

    }

}
