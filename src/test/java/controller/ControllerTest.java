
import controller.TestGraphFactory;
import model.BusDijkstra;
import model.QGram;
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
    System.out.println(path);
    List<String> sPath = new ArrayList<>();
    for (BusDijkstra.StopNode stopNode : path) {
      sPath.add(stopNode.toString());
    }

    Assertions.assertArrayEquals(new String[]{"A", "A", "B", "C"}, sPath.toArray());

  }

  @Test
  void testSimilarity() {
    Assertions.assertEquals("0.6000", String.format("%.4f", QGram.similarity("cafe p","cafe palacio")));
    Assertions.assertEquals("0.6000", String.format("%.4f", QGram.similarity("rubi","ruby")));
    Assertions.assertEquals("0.3077", String.format("%.4f", QGram.similarity("salesal","vale")));
    Assertions.assertEquals("0.4286", String.format("%.4f", QGram.similarity("salesal","alale")));
  }

  @Test
  void testSearchLocation() {
    //TODO
  }
}
