package hr.fer.zemris.optjava.dz13.ant.ga;

import hr.fer.zemris.optjava.dz13.ant.ga.solution.AntGPSolution;
import hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes.AbstractGPNode;
import hr.fer.zemris.optjava.ga.generic.operators.IMutation;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class AntMutation implements IMutation<AntGPSolution> {
    private final int maxNodes;
    private final int maxDepth;
    private final int maxCreateDepth;
    private final IRNG rnd;
    private final GPTreeConstructor treeConstructor;

    public AntMutation(final int maxNodes, final int maxDepth, final int maxCreateDepth,
            final GPTreeConstructor treeConstructor) {
        super();
        this.maxNodes = maxNodes;
        this.maxDepth = maxDepth;
        this.maxCreateDepth = maxCreateDepth;
        this.treeConstructor = treeConstructor;
        rnd = RNG.getRNG();
    }

    @Override
    public AntGPSolution mutate(final AntGPSolution individual) {
        AbstractGPNode data = individual.getData().copy();
        AbstractGPNode newNode = treeConstructor.constructSubtree(maxCreateDepth, maxNodes, rnd.nextBoolean());

        data = data.replaceRandomNode(newNode);
        data.calculateNChildren();

        if (data.nChildrenInSubtree > maxNodes || data.depth > maxDepth) {
            return new AntGPSolution(individual.getData().copy());
        }
        return new AntGPSolution(data);
    }
}
