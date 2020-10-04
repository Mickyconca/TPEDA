import model.BusDijkstra;
import model.BusInPath;
import model.PlaceLocation;
import model.SearchLocation;

import java.util.Arrays;
import java.util.List;

public class Controller {

  BusDijkstra bus;
  SearchLocation location;

  public Controller(BusDijkstra bus, SearchLocation location) {
    this.bus = bus;
    this.location = location;
  }

  public List<BusInPath> findPath(double fromLat, double fromLng, double toLat, double toLng) {
    return bus.path(fromLat, fromLng, toLat, toLng);
  }

  public List<PlaceLocation> findPlaces(String searchTerm) {
    return location.search(searchTerm);
  }
}
