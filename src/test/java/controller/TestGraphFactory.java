package controller;

import model.BusDijkstra;

public class TestGraphFactory {

    public static BusDijkstra createGraph() {

        BusDijkstra graph = new BusDijkstra( true);
        graph.addNode("A", "A",   1.0, 1.0, 1);
        graph.addNode("B", "B", 1.05, 1.015, 1);
        graph.addNode("C", "C", 2.4, 3.5,1);
        graph.addNode("D", "D", 2.34, 3.5, 1);
        graph.addNode("E", "E", 7.89, 5.5, 1);
        graph.addNode("F", "F", 7.75, 5.43, 1);
        graph.addNode("G", "G", 6.45, 5.4, 1);

        graph.addEdge("A", "B", 2);
        graph.addEdge("A", "C", 4);
        graph.addEdge("B", "D", 6);
        graph.addEdge("C", "D", 2);
        graph.addEdge("C", "F", 1);
        graph.addEdge("F", "E", 4);
        graph.addEdge("E", "G", 2);
        graph.addEdge("D", "E", 1);

        return graph;
    }


}
