package hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.AntMap;

public class AntMoveNode extends AbstractGPNode {

    @Override
    public AbstractGPNode copy() {
        return new AntMoveNode();
    }

    @Override
    public String toString() {
        return "Move";
    }

    @Override
    public int execute(final AntMap map, final Ant ant) {
        ant.move(map);
        return 1;
    }

    @Override
    public int numberOfChildren() {
        return 0;
    }

}
