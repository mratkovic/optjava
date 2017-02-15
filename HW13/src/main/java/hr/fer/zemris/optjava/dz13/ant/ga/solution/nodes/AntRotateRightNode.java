package hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.AntMap;

public class AntRotateRightNode extends AbstractGPNode {

    @Override
    public AbstractGPNode copy() {
        return new AntRotateRightNode();
    }

    @Override
    public String toString() {
        return "Left";
    }

    @Override
    public int execute(final AntMap map, final Ant ant) {
        ant.rotateLeft();
        return 1;
    }

    @Override
    public int numberOfChildren() {
        return 0;
    }

}
