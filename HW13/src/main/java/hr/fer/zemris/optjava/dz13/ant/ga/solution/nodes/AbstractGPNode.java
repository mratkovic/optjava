package hr.fer.zemris.optjava.dz13.ant.ga.solution.nodes;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.AntMap;
import hr.fer.zemris.optjava.rng.RNG;

public abstract class AbstractGPNode {
    public List<AbstractGPNode> children;
    public int depth;
    public int nChildrenInSubtree;

    public AbstractGPNode(final List<AbstractGPNode> children, final int depth, final int nChildrenInSubtree) {
        super();
        this.children = children;
        this.depth = depth;
        this.nChildrenInSubtree = nChildrenInSubtree;
    }

    public AbstractGPNode() {
        super();
        children = new ArrayList<>();
    }

    protected List<AbstractGPNode> childrenCopy() {
        List<AbstractGPNode> childrenCopy = new ArrayList<>();
        for (AbstractGPNode child : children) {
            childrenCopy.add(child.copy());
        }
        return childrenCopy;
    }

    public abstract AbstractGPNode copy();

    public abstract int numberOfChildren();

    public abstract int execute(AntMap map, Ant ant);

    public AbstractGPNode getChild(final int index) {
        if (index >= children.size()) {
            throw new IllegalArgumentException(
                    "Invalid child, node has " + children.size() + " children, asked for " + index);
        }
        return children.get(index);
    }

    public void addChild(final AbstractGPNode node) {
        children.add(node);
    }

    public int calculateNChildren() {
        nChildrenInSubtree = 0;
        depth = 1;
        for (AbstractGPNode child : children) {
            nChildrenInSubtree += child.calculateNChildren() + 1;
            depth = Math.max(depth, child.depth + 1);
        }
        return nChildrenInSubtree;
    }

    public AbstractGPNode pickRandomNode() {
        double choice = RNG.getRNG().nextDouble();
        double sumProb = 0;

        for (AbstractGPNode child : children) {
            sumProb += ((double) child.nChildrenInSubtree + 1) / (nChildrenInSubtree + 1);
            if (choice < sumProb) {
                return child.pickRandomNode();
            }
            assert sumProb > 1.0;

        }
        return this;
    }

    public AbstractGPNode replaceRandomNode(final AbstractGPNode newNode) {
        double choice = RNG.getRNG().nextDouble();
        double sumProb = 0;

        for (int i = 0; i < numberOfChildren(); ++i) {
            AbstractGPNode child = children.get(i);
            sumProb += ((double) child.nChildrenInSubtree + 1) / (nChildrenInSubtree + 1);
            if (choice < sumProb) {
                children.set(i, child.replaceRandomNode(newNode));
                return this;
            }
            assert sumProb > 1.0;
        }

        return newNode;
    }

    public void setChild(final int i, final AbstractGPNode newNode) {
        children.set(i, newNode);
    }

    protected List<AbstractGPNode> cloneChildren() {
        List<AbstractGPNode> ret = new ArrayList<>();
        for (AbstractGPNode child : children) {
            ret.add(child.copy());
        }

        return ret;
    }

    public String serializeToString() {
        StringBuilder sb = new StringBuilder();
        serializeToString(sb, 0);
        return sb.toString();
    }

    public void serializeToString(final StringBuilder sb, final int depth) {
        appendPadding(sb, depth);
        sb.append(this.toString() + "\n");

        for (int i = 0; i < numberOfChildren(); ++i) {
            getChild(i).serializeToString(sb, depth + 1);
        }
    }

    private void appendPadding(final StringBuilder sb, final int nSpaces) {
        for (int i = 0; i < nSpaces; ++i) {
            sb.append("  ");
        }
    }

}
