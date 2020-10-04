import model.BusDijkstra;
import model.BusInPath;
import model.PlaceLocation;

import java.util.Arrays;
import java.util.List;

public class Controller {

  BusDijkstra bus;

  public Controller(BusDijkstra bus) {
    this.bus = bus;
  }

  public List<BusInPath> findPath(double fromLat, double fromLng, double toLat, double toLng) {
    return bus.path(fromLat, fromLng, toLat, toLng);
  }

  public List<PlaceLocation> findPlaces(String searchTerm) {
    return Arrays.asList(new PlaceLocation("No implementado"));
  }
}
