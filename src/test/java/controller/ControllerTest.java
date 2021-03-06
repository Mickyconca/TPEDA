package controller;

import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ControllerTest {

  @Test
  void testDijkstra(){
    BusDijkstra testgraph = TestGraphFactory.createGraph();
    List<BusDijkstra.StopNode> path= testgraph.pathDijkstra(testgraph.getNode("A"), testgraph.getNode("C"));

    List<String> sPath = new ArrayList<>();
    for (BusDijkstra.StopNode stopNode : path) {
      sPath.add(stopNode.toString());
    }
    Assertions.assertArrayEquals(new String[]{"A", "A", "B", "B", "C"}, sPath.toArray());   // shortName

    String[] sAux = new String[]{"A", "A3", "B", "B1", "C"};
    int i=0;
    for (BusDijkstra.StopNode stopNode : path) {
      Assertions.assertEquals(sAux[i++], stopNode.getStopId());     // stopId
    }
    sPath.clear();

    List<BusInPath> path2 = testgraph.path(3.9995, 4.0,8.0, 8.0065);    // va a elegir caminar antes que hacer una combinacion de mas
                                                                                                      // es decir, no se toma la linea A cuando puede caminar hasta la B
    for (BusInPath bus : path2) {
      sPath.add(bus.name);
    }
    Assertions.assertArrayEquals(new String[]{"B", "C"}, sPath.toArray());       // caminar, "B", "B1", "C", "C3", caminar
  }

  @Test
  void testSimilarity() {
    Assertions.assertEquals("0,6000", String.format("%.4f", QGram.similarity("cafe p","cafe palacio")));
    Assertions.assertEquals("0,6000", String.format("%.4f", QGram.similarity("rubi","ruby")));
    Assertions.assertEquals("0,3077", String.format("%.4f", QGram.similarity("salesal","vale")));
    Assertions.assertEquals("0,4286", String.format("%.4f", QGram.similarity("salesal","alale")));
    Assertions.assertEquals("0,6667", String.format("%.4f", QGram.similarity("salesal","salale")));
  }

  @Test
  void testSearchLocation() {
    SearchLocation locations = TestSearchFactory.createLocations();
    List<PlaceLocation> locationsFound = locations.search("rubi");
    List<PlaceLocation> check = new ArrayList<>();

    check.add(new PlaceLocation("rubi", 1, 1));
    check.add(new PlaceLocation("rub", 1, 1));
    check.add(new PlaceLocation("ruby", 1, 1));
    check.add(new PlaceLocation("ruvy", 1, 1));
    Assertions.assertEquals(check, locationsFound);

    check.add(new PlaceLocation("alale", 1, 1));
    Assertions.assertNotEquals(check, locationsFound);

    check.clear();

    locationsFound = SearchLocation.search("salesal");
    check.add(new PlaceLocation("salale", 1, 1));
    check.add(new PlaceLocation("alale", 1, 1));
    Assertions.assertEquals(check, locationsFound);

    check.add(new PlaceLocation("vale", 1, 1));
    Assertions.assertNotEquals(check, locationsFound);
  }
}
