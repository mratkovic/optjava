package hr.fer.zemris.optjava.dz13.ant.ga;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes.AbstractGPNode;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class GPTreeConstructor {
    private final List<AbstractGPNode> functionNodes;
    private final List<AbstractGPNode> terminalNodes;
    private final List<AbstractGPNode> allNodes;
    private final IRNG rnd;

    public GPTreeConstructor(final List<AbstractGPNode> functionNodes, final List<AbstractGPNode> terminalNodes) {
        super();
        this.functionNodes = functionNodes;
        this.terminalNodes = terminalNodes;
        this.allNodes = new ArrayList<>();
        this.allNodes.addAll(functionNodes);
        this.allNodes.addAll(terminalNodes);
        rnd = RNG.getRNG();
    }

    public AbstractGPNode constructTree(final int depth, final int maxNodes, final boolean grow) {
        // minimum depth atleast 2
        AbstractGPNode root = randomNode(functionNodes);
        constructSubtree(root, 2, depth, maxNodes, grow);
        root.calculateNChildren();
        return root;
    }

    public AbstractGPNode constructSubtree(final int depth, final int maxNodes, final boolean grow) {
        // allows tree of dept 1 (all nodes)
        AbstractGPNode root = randomNode(allNodes);
        constructSubtree(root, 2, depth, maxNodes, grow);
        root.calculateNChildren();
        return root;
    }

    private AbstractGPNode randomNode(final List<AbstractGPNode> nodes) {
        return nodes.get(rnd.nextInt(0, nodes.size())).copy();
    }

    private void constructSubtree(final AbstractGPNode root, final int currentDepth, final int maxDepth, int nodesLeft,
            final boolean grow) {
        for (int i = 0; i < root.numberOfChildren(); ++i) {
            if (currentDepth >= maxDepth || nodesLeft < 0) {
                // dont let trees be to deep nor wide
                // width of tree modeled as nodesLeft
                // if no nodes left - use only terminal nodes to stop growth
                root.addChild(randomNode(terminalNodes));
                nodesLeft--;

            } else {
                AbstractGPNode child = grow ? randomNode(functionNodes) : randomNode(allNodes);
                root.addChild(child);
                constructSubtree(child, currentDepth + 1, maxDepth, nodesLeft, grow);
                nodesLeft -= child.calculateNChildren();
            }
        }

    }
}
