package controller;

import model.BusDijkstra;
import org.junit.jupiter.api.Test;

import java.util.List;

class ControllerTest {

  @Test
  void asd() {
    System.out.println("TODO");
  }

  @Test
  void testDijkstra(){
    BusDijkstra graph = TestGraphFactory.createGraph();

    List<BusDijkstra.StopNode> path= graph.pathDijkstra(graph.getNode("A"), graph.getNode("E"));
    System.out.println("Path: [");
    for (BusDijkstra.StopNode stopNode : path) {
      System.out.println(stopNode);
    }
    System.out.println("]");

  }

}