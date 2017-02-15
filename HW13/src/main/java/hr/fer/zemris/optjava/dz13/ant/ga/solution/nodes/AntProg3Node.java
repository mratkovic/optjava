package hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes;

import java.util.List;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.AntMap;

public class AntProg3Node extends AbstractGPNode {

    public AntProg3Node() {
        super();
    }

    public AntProg3Node(final List<AbstractGPNode> children, final int depth, final int nChildrenInSubtree) {
        super(children, depth, nChildrenInSubtree);
    }

    @Override
    public AbstractGPNode copy() {
        return new AntProg3Node(cloneChildren(), depth, nChildrenInSubtree);
    }

    @Override
    public String toString() {
        return "Prog3";
    }

    @Override
    public int execute(final AntMap map, final Ant ant) {
        return getChild(0).execute(map, ant) + getChild(1).execute(map, ant) + getChild(2).execute(map, ant);
    }

    @Override
    public int numberOfChildren() {
        return 3;
    }

}
