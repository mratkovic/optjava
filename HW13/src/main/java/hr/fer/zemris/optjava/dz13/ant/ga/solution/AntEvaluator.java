package hr.fer.zemris.optjava.dz13.ant.ga.solution;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.AntMap;
import hr.fer.zemris.optjava.dz13.ant.Direction;
import hr.fer.zemris.optjava.ga.IFunction;

public class AntEvaluator implements IFunction<AntGPSolution> {

    private final AntMap map;
    private final int maxSteps;

    public AntEvaluator(final AntMap map, final int maxSteps) {
        super();
        this.map = map;
        this.maxSteps = maxSteps;
    }

    @Override
    public double valueAt(final AntGPSolution antSolution) {
        int score = 0;
        AntMap mapCopy = map.copy();
        Ant ant = new Ant(0, 0, Direction.RIGHT);

        while (ant.getStepsTaken() <= maxSteps) {
            score = ant.getFoodEaten();
            antSolution.execute(mapCopy, ant);
        }
        return score;
    }

}
