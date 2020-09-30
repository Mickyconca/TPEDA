package model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BusDijkstra {

    class Node implements Comparable<Node>{
        String label;
        Set<Edge> edges;
        double distance = Double.MAX_VALUE;
        boolean visited;

        public Node(String label) {
            this.label = label;
            this.visited = false;
            edges = new HashSet<>();
        }

        @Override
        public int hashCode(){
            return Objects.hash(label);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node node = (Node) o;
            return Objects.equals(label, node.label);
        }

        @Override
        public int compareTo(Node o) {
            return 0;
        }
    }

    class Edge{
        Node target;
        double weigth;

        public Edge(Node target, double weigth) {
            this.target = target;
            this.weigth = weigth;
        }
    }
}
