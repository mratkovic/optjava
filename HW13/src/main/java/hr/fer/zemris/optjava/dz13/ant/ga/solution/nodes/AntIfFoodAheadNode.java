package hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes;

import java.util.List;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.AntMap;

public class AntIfFoodAheadNode extends AbstractGPNode {

    public AntIfFoodAheadNode() {
        super();
    }

    public AntIfFoodAheadNode(final List<AbstractGPNode> children, final int depth, final int nChildrenInSubtree) {
        super(children, depth, nChildrenInSubtree);
        // TODO Auto-generated constructor stub
    }

    @Override
    public AbstractGPNode copy() {
        return new AntIfFoodAheadNode(cloneChildren(), depth, nChildrenInSubtree);
    }

    @Override
    public String toString() {
        return "IfFoodAhead";
    }

    @Override
    public int execute(final AntMap map, final Ant ant) {
        if (map.isFoodAhead(ant)) {
            return getChild(0).execute(map, ant);
        } else {
            return getChild(1).execute(map, ant);
        }
    }

    @Override
    public int numberOfChildren() {
        return 2;
    }

}
