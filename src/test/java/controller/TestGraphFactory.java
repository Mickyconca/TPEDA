package controller;

import model.BusDijkstra;

public class TestGraphFactory {

    public static BusDijkstra createGraph() {

        BusDijkstra testgraph = new BusDijkstra( false);
        testgraph.addNode("A", "A",   1.0, 1.0, 1);
        testgraph.addNode("A1", "A",   1.002, 1.002, 1);
        testgraph.addNode("A2", "A",   1.004, 1.004, 1);
        testgraph.addNode("A3", "A", 1.006, 1.006, 1);      // |lat1 - lat2| + |long1 - long2| < 0.005  distance
        testgraph.addNode("B", "B", 1.0065, 1.0069,1);        // |lat1 - lat2| + |long1 - long2| < 0.002  distanceCombination
        testgraph.addNode("B1", "B", 1.0075, 1.0078, 1);    // |lat1 - lat2| + |long1 - long2| < 0.0333  distanceWalked
        testgraph.addNode("C", "C", 1.0085, 1.0078, 1);
        testgraph.addNode("C1", "C", 1.0095, 1.0087, 1);
        testgraph.addNode("C2", "C", 1.0105, 1.0096, 1);
        testgraph.addNode("C3", "C", 1.0115, 1.0105, 1);
        testgraph.addNode("-A4", "A", 1.007, 1.0069, 0);     // no deberia elegirlo porque hace distanceCombination por tener diferente direccion
        testgraph.addNode("-A5", "A", 1.008, 1.0069, 0);


        testgraph.addEdges();

        return testgraph;
    }


}
