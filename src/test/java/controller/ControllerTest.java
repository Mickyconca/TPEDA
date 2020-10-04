package controller;

import model.BusDijkstra;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ControllerTest {

  @Test
  void asd() {
    System.out.println("TODO");
  }

  @Test
  void testDijkstra(){
    BusDijkstra graph = TestGraphFactory.createGraph();

    List<BusDijkstra.StopNode> path= graph.pathDijkstra(graph.getNode("A"), graph.getNode("C"));
    List<String> sPath = new ArrayList<>();
    for (BusDijkstra.StopNode stopNode : path) {
      sPath.add(stopNode.toString());
    }

    Assertions.assertArrayEquals(new String[]{"A", "A", "B", "C"}, sPath.toArray());


  }

}
