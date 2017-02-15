package hr.fer.zemris.optjava.dz13.ant.ga;

import hr.fer.zemris.optjava.dz13.ant.ga.solution.AntGPSolution;
import hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes.AbstractGPNode;
import hr.fer.zemris.optjava.ga.generic.operators.ICross;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class AntCrossover implements ICross<AntGPSolution> {
    private final int maxNodes;
    private final int maxDepth;
    private final IRNG rnd;
    private final GPTreeConstructor treeConstructor;

    public AntCrossover(final int maxNodes, final int maxDepth, final GPTreeConstructor treeConstructor) {
        super();
        this.maxNodes = maxNodes;
        this.maxDepth = maxDepth;
        this.treeConstructor = treeConstructor;
        rnd = RNG.getRNG();
    }

    public AntGPSolution mutate(final AntGPSolution individual) {
        AbstractGPNode data = individual.getData().copy();

        AbstractGPNode newNode = treeConstructor.constructSubtree(maxDepth, maxNodes, rnd.nextBoolean());
        AbstractGPNode parent = data.pickRandomNode();
        if (parent.numberOfChildren() == 0) {
            // change root
            return new AntGPSolution(newNode);
        }

        int randomChild = rnd.nextInt(0, parent.numberOfChildren());
        parent.setChild(randomChild, newNode);
        data.calculateNChildren();

        if (data.nChildrenInSubtree > maxNodes || data.depth > maxDepth) {
            return new AntGPSolution(individual.getData().copy());
        }
        return new AntGPSolution(data);
    }

    @Override
    public AntGPSolution crossParents(final AntGPSolution p1, final AntGPSolution p2) {
        AbstractGPNode newRoot = p1.getData().copy();
        AbstractGPNode p2Root = p2.getData().copy();

        AbstractGPNode p2Rnd = p2Root.pickRandomNode();

        newRoot.replaceRandomNode(p2Rnd);
        newRoot.calculateNChildren();

        if (newRoot.nChildrenInSubtree > maxNodes || newRoot.depth > maxDepth) {
            return new AntGPSolution(p1.getData().copy());
        }

        return new AntGPSolution(newRoot);
    }
}
