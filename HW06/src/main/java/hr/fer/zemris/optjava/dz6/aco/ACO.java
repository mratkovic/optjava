package hr.fer.zemris.optjava.dz6.aco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import hr.fer.zemris.optjava.dz6.tsp.Graph;
import hr.fer.zemris.optjava.dz6.tsp.Node;
import hr.fer.zemris.optjava.dz6.util.Pair;

public class ACO {
    private static final int STUCK_COUTNER = 5000;
    // Metaparametri
    public double alpha = 5;
    public double beta = 1.5;
    public final int k;
    public double ro = 0.02;
    public final double a;
    public int colonySize;

    public double tauMax;
    public double tauMin;

    Graph graph;
    int n;
    private final double[][] fitness;
    private final double[][] pheromone;

    Map<Integer, List<Node>> neighbors;
    Random rnd;

    public ACO(final Graph graph, final double alpha, final double beta, final int k, final double ro,
            final int colonySize) {
        this.graph = graph;
        n = graph.n;

        this.alpha = alpha;
        this.beta = beta;
        this.ro = ro;
        this.k = k;
        this.colonySize = colonySize;
        a = graph.n / k;

        pheromone = new double[n][n];
        fitness = new double[n][n];
        initFitness();
        initNeighbors();

        double greedyDistance = runGreedy();
        tauMax = 1 / (ro * greedyDistance);
        tauMin = tauMax / a;

        rnd = new Random();

    }

    private void initNeighbors() {
        neighbors = new HashMap<>();

        for (int i = 0; i < graph.n; i++) {
            List<Pair<Node, Double>> tmp = new ArrayList<>();
            for (Entry<Node, Double> e : graph.getDistancesFrom(i).entrySet()) {
                tmp.add(new Pair<>(e.getKey(), e.getValue()));
            }
            tmp.sort((x, y) -> Double.compare(x.second, y.second));

            List<Node> neighborList = new LinkedList<>();
            for (int j = 0; j < k; j++) {
                neighborList.add(tmp.get(j).first);
            }
            neighbors.put(i, neighborList);
        }
    }

    private void initFitness() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    fitness[i][j] = Math.pow(1.0 / graph.distance[i][j], beta);
                }
            }
        }
    }

    private void initPheromoneValue(final double value) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                pheromone[i][j] = pheromone[j][i] = value;
            }
        }
    }

    private static double clip(double val, final double min, final double max) {
        val = Math.max(min, val);
        val = Math.min(max, val);
        return val;
    }

    private void evaporatePheromoneValues() {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                pheromone[j][i] = clip(pheromone[j][i] * (1 - ro), tauMin, tauMax);

            }
        }
    }

    private void updatePheromoneValues(final Ant ant) {
        List<Node> path = ant.path;
        for (int i = 0; i < path.size() - 1; ++i) {
            int curr = path.get(i).id;
            int next = path.get(i + 1).id;
            pheromone[curr][next] = clip(pheromone[curr][next] + ant.getFitness(), tauMin, tauMax);

        }
    }

    public double runGreedy() {
        Set<Node> visited = new HashSet<>();
        LinkedList<Node> path = new LinkedList<>();
        double distance = 0;

        Node start = graph.nodes.get(0);
        visited.add(start);
        path.add(start);

        while (visited.size() < graph.n) {
            Node last = path.getLast();

            List<Node> candidates = neighbors.get(last.id).stream().filter(n -> !visited.contains(n))
                    .collect(Collectors.toList());
            if (candidates.isEmpty()) {
                candidates = graph.nodes.stream().filter(n -> !visited.contains(n)).collect(Collectors.toList());
            }

            for (Node next : candidates) {
                if (!visited.contains(next)) {
                    distance += graph.distance[last.id][next.id];
                    path.add(next);
                    visited.add(next);
                    break;
                }
            }
        }

        Node last = path.getLast();
        distance += graph.distance[last.id][start.id];
        return distance;
    }

    public Ant run(final int maxIter) {
        int iter = 0;
        Ant best = null;
        int stuckCounter = 0;

        while (iter++ < maxIter) {
            Ant colonyBest = releaseAnts();
            evaporatePheromoneValues();

            if (best == null || colonyBest.getFitness() > best.getFitness()) {
                best = colonyBest;
                stuckCounter = 0;

            }
            // mix colony best and global best
            // not much difference
            if (iter % 10 == 0) {
                updatePheromoneValues(best);
            } else {
                updatePheromoneValues(colonyBest);
            }

            System.out.printf("%5d\tbest: %6.3f\tcolony_best: %6.3f\n", iter, best.distance, colonyBest.distance);
            stuckCounter++;
            if (stuckCounter == STUCK_COUTNER) {
                // doesn't really help that much if stuck
                System.out.println("RESET");
                tauMax = 1 / (ro * best.distance);
                tauMin = tauMax / a;
                initPheromoneValue(tauMax);
                stuckCounter = 0;
            }

        }

        return best;
    }

    private Ant releaseAnts() {
        List<Ant> ants = new ArrayList<>();
        for (int i = 0; i < colonySize; ++i) {
            Ant ant = new Ant();
            Node start = graph.nodes.get(rnd.nextInt(n));
            ant.addNode(start, 0);

            while (ant.routeLength() != n) {
                Node next = selectNext(ant);
                nextOnPath(ant, next);
            }
            nextOnPath(ant, start);
            ants.add(ant);
        }
        Ant colonyBest = ants.stream().min((a1, a2) -> a1.compareTo(a2)).orElse(null);
        return colonyBest;

    }

    private Node selectNext(final Ant a) {
        double sum = 0;
        LinkedList<Pair<Node, Double>> probs = new LinkedList<>();
        int current = a.getCurrentNode().id;

        List<Node> candidates = neighbors.get(current).stream().filter(n -> !a.visited(n)).collect(Collectors.toList());
        if (candidates.isEmpty()) {
            candidates = graph.nodes.stream().filter(n -> !a.visited(n)).collect(Collectors.toList());
        }

        for (Node next : candidates) {
            double tau = pheromone[current][next.id];
            double p = Math.pow(tau, alpha) * fitness[current][next.id];
            probs.add(new Pair<>(next, p));
            sum += p;
        }

        double value = rnd.nextDouble() * sum;
        double cumulativeFittnes = 0;
        for (Pair<Node, Double> p : probs) {
            cumulativeFittnes += p.second;
            if (value <= cumulativeFittnes) {
                return p.first;
            }
        }
        return probs.getLast().first;

    }

    private double distance(final Ant a, final Node next) {
        return graph.distance[a.getCurrentNode().id][next.id];
    }

    private void nextOnPath(final Ant a, final Node node) {
        a.addNode(node, distance(a, node));
    }

}
