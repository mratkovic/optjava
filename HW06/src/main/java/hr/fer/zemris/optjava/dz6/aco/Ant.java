package hr.fer.zemris.optjava.dz6.aco;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import hr.fer.zemris.optjava.dz6.tsp.Node;

public class Ant implements Comparable<Ant> {
    public final LinkedList<Node> path;
    public double distance;
    private final Set<Node> visited;

    public Ant() {
        this.path = new LinkedList<>();
        distance = 0;
        visited = new HashSet<>();
    }

    public void addNode(final Node node, final double dist) {
        if (!visited(node) || node.equals(start())) {
            this.distance += dist;
            path.add(node);
            visited.add(node);

        } else {
            throw new IllegalArgumentException("Already visited");
        }
    }

    public Node start() {
        if (path.isEmpty()) {
            return null;
        }
        return path.get(0);
    }

    public Node getCurrentNode() {
        return path.getLast();
    }

    public double getFitness() {
        return 1 / distance;
    }

    public boolean visited(final Node c) {
        return visited.contains(c);
    }

    public int routeLength() {
        return path.size();
    }

    @Override
    public int compareTo(final Ant o) {
        return Double.compare(distance, o.distance);
    }

    public void printPath() {
        for (Node next : path) {
            System.out.println(next.id + 1);
        }
    }

}