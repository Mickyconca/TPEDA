package controller;

import model.BusDijkstra;

public class TestGraphFactory {

    public static BusDijkstra createGraph() {

        BusDijkstra graph = new BusDijkstra( false);
        graph.addNode("A", "A",   1.0, 1.0, 1);
        graph.addNode("A1", "A",   1.0001, 1.0, 1);
        graph.addNode("A", "A",   1.0, 1.0, 0);
        graph.addNode("B", "B", 1.001, 1.001, 1);
        graph.addNode("C", "C", 1.002, 1.001,1);
        graph.addNode("D", "D", 2.34, 3.5, 1);
        graph.addNode("E", "E", 7.89, 5.5, 1);
        graph.addNode("F", "F", 7.75, 5.43, 1);
        graph.addNode("G", "G", 6.45, 5.4, 1);

        graph.addEdges();       // menor a 0.001

        return graph;
    }


}
