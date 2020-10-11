package controller;

import model.BusDijkstra;

public class TestGraphFactory {

    public static BusDijkstra createGraph() {

        BusDijkstra testgraph = new BusDijkstra( false);
        testgraph.addNode("A", "A",   1.0, 1.0, 1);
        testgraph.addNode("A1", "A",   2.0, 2.0, 1);
        testgraph.addNode("A2", "A",   3.0, 3.0, 1);
        testgraph.addNode("A3", "A", 4.0, 4.0, 1);      // sqrt((lat1 - lat2)^2 + (long1 - long2)^2) < 0.007 -> distance < RADIO
        testgraph.addNode("B", "B", 4.006, 4.0,1);      // sqrt((lat1 - lat2)^2 + (long1 - long2)^2) + 2 ->  weight when different buses
        testgraph.addNode("B1", "B", 5.0, 5.0, 1);    // sqrt((lat1 - lat2)^2 + (long1 - long2)^2) + 1 ->  weight addMapNode
        testgraph.addNode("C", "C", 5.0, 5.006, 1);
        testgraph.addNode("C1", "C", 6.0, 6.0, 1);
        testgraph.addNode("C2", "C", 7.0, 7.0, 1);
        testgraph.addNode("C3", "C", 8.0, 8.0, 1);
        testgraph.addNode("-A4", "A", 3.01, 3.0, 0);     // no deberia recorrerlo por tener diferente direccion
        testgraph.addNode("-A5", "A", 4.01, 4.0, 0);


        testgraph.addEdges();

        return testgraph;
    }


}
