package controller;

import model.BusDijkstra;

public class TestGraphFactory {

    public static BusDijkstra createGraph() {

        BusDijkstra graph = new BusDijkstra( false);
        graph.addNode("A", "A",   1.0, 1.0, 1);
        graph.addNode("A1", "A",   1.002, 1.002, 1);
        graph.addNode("A2", "A",   1.004, 1.004, 1);
        graph.addNode("A3", "A", 1.006, 1.006, 1);      // |lat1 - lat2| + |long1 - long2| < 0.005  distance
        graph.addNode("B", "B", 1.0065, 1.0069,1);        // |lat1 - lat2| + |long1 - long2| < 0.002  distanceCombination
        graph.addNode("B1", "B", 1.0075, 1.0078, 1);    // |lat1 - lat2| + |long1 - long2| < 0.0333  distanceWalked
        graph.addNode("C", "C", 1.0085, 1.0078, 1);
        graph.addNode("C1", "C", 1.0095, 1.0087, 1);
        graph.addNode("C2", "C", 1.0105, 1.0096, 1);
        graph.addNode("C3", "C", 1.0115, 1.0105, 1);
        graph.addNode("-A4", "A", 1.007, 1.0069, 0);     // no deberia elegirlo porque hace distanceCombination por tener diferente direccion
        graph.addNode("-A5", "A", 1.008, 1.0069, 0);


        graph.addEdges();

        return graph;
    }


}
