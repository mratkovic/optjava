package hr.fer.zemris.optjava.dz6.tsp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Graph {
    public double[][] distance;
    public List<Node> nodes;
    public int n;

    public Graph(final String path) throws IOException {
        super();
        parse(path);
    }

    private void parse(final String path) throws IOException {
        Scanner sc = new Scanner(new File(path));
        String str = null;
        while (!(str = sc.nextLine()).startsWith("DIMENSION")) {
            ;// pass
        }

        n = Integer.parseInt(str.split(":")[1].trim());
        distance = new double[n][n];

        while (Character.isAlphabetic((str = sc.nextLine()).trim().charAt(0))) {
            ;// pass
        }

        nodes = new LinkedList<>();
        while (!(str).equals("EOF")) {
            String[] token = str.trim().split("\\s+");
            nodes.add(new Node(nodes.size(), Double.parseDouble(token[1]), Double.parseDouble(token[2])));
            str = sc.nextLine();
        }

        for (int i = 0; i < n; i++) {
            Node from = nodes.get(i);

            for (int j = i + 1; j < n; j++) {
                Node to = nodes.get(j);
                distance[j][i] = distance[i][j] = from.distance(to);
            }
        }
        sc.close();
    }

    public Map<Node, Double> getDistancesFrom(final int from) {
        HashMap<Node, Double> distMap = new HashMap<>();

        for (Node n : nodes) {
            if (n.id != from) {
                distMap.put(n, distance[from][n.id]);
            }
        }
        return distMap;
    }

    public static Graph parseProblem(final String path) throws IOException {
        return new Graph(path);
    }
}
