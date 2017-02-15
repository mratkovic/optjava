package hr.fer.zemris.optjava.dz13.ant.ga;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz13.ant.ga.solution.AntGPSolution;
import hr.fer.zemris.optjava.ga.generic.operators.IPopulationInitializer;

public class RampedHalfAndHalfInitializer implements IPopulationInitializer<AntGPSolution> {
    private final int maxInitialDepth;
    private final int maxNodes;
    private final int minDepth = 2;
    private final GPTreeConstructor treeConstructor;

    public RampedHalfAndHalfInitializer(final int maxInitialDepth, final int maxNodes,
            final GPTreeConstructor treeConstructor) {
        super();
        this.maxInitialDepth = maxInitialDepth;
        this.maxNodes = maxNodes;
        this.treeConstructor = treeConstructor;
    }

    @Override
    public List<AntGPSolution> initialize(final int popSize) {
        int differentDepths = maxInitialDepth - minDepth + 1;
        List<AntGPSolution> population = new ArrayList<>();
        boolean grow = true;

        for (int i = 0; i < popSize; ++i) {
            int depth = minDepth + i % differentDepths;
            AntGPSolution sol = constructSolution(depth, grow);
            population.add(sol);

            // System.out.println(i + " " + depth + " " + grow + " -> " +
            // sol.getData().depth + " "
            // + sol.getData().nChildrenInSubtree);
            // toggle after epoch
            if (depth == maxInitialDepth) {
                grow = !grow;
            }
        }

        return population;
    }

    private AntGPSolution constructSolution(final int depth, final boolean grow) {
        return new AntGPSolution(treeConstructor.constructTree(depth, maxNodes, grow));
    }

}
