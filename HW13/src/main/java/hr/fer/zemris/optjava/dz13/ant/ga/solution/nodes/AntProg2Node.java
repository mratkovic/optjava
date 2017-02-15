package hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes;

import java.util.List;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.AntMap;

public class AntProg2Node extends AbstractGPNode {

    public AntProg2Node() {
        super();
    }

    public AntProg2Node(final List<AbstractGPNode> children, final int depth, final int nChildrenInSubtree) {
        super(children, depth, nChildrenInSubtree);
        // TODO Auto-generated constructor stub
    }

    @Override
    public AbstractGPNode copy() {
        return new AntProg2Node(cloneChildren(), depth, nChildrenInSubtree);
    }

    @Override
    public String toString() {
        return "Prog2";
    }

    @Override
    public int execute(final AntMap map, final Ant ant) {
        return getChild(0).execute(map, ant) + getChild(1).execute(map, ant);
    }

    @Override
    public int numberOfChildren() {
        return 2;
    }

}
