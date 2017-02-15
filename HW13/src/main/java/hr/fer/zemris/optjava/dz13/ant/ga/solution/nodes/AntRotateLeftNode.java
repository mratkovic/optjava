package hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.AntMap;

public class AntRotateLeftNode extends AbstractGPNode {

    @Override
    public AbstractGPNode copy() {
        return new AntRotateLeftNode();
    }

    @Override
    public String toString() {
        return "Right";
    }

    @Override
    public int execute(final AntMap map, final Ant ant) {
        ant.rotateRight();
        return 1;
    }

    @Override
    public int numberOfChildren() {
        return 0;
    }

}
